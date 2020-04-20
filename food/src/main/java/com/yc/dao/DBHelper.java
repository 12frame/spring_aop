package com.yc.dao;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.yc.utils.LogUtil;

/**
 * 数据库联接帮助类
 */
public class DBHelper<T> {

	// 保证数据库驱动要在应用程序加载加载起来.
	static {
		try {
			Class.forName(MyProperties.getInstance().getProperty("driverName"));
		} catch (ClassNotFoundException e) {
			LogUtil.error(e);
			LogUtil.logger.error("驱动程序错误，系统安全退出...");
			System.exit(0);
		}
	}

	/**
	 * 获取联接
	 */
	public Connection getCon() {
		Connection con = null;
		String url = MyProperties.getInstance().getProperty("url");
		String userName = MyProperties.getInstance().getProperty("userName");
		String password = MyProperties.getInstance().getProperty("password");
		try {
			con = DriverManager.getConnection(url, userName, password);
		} catch (SQLException e) {
			LogUtil.error(e);
		}

		// try {
		// Context initCtx = new InitialContext();
		// Context envCtx = (Context) initCtx.lookup("java:comp/env");
		// DataSource ds = (DataSource) envCtx.lookup("jdbc/mysql");
		// con = ds.getConnection();
		// } catch (Exception e) {
		// e.printStackTrace();
		// }

		return con;
	}

	/**
	 * 用于update, delete, insert或 ddl操作的方法
	 * 
	 * @param sql:
	 *            可以带?
	 * @param params
	 *            : 是?对应的参数值列表
	 * @return : 受影响的行数
	 * @throws SQLException 
	 */
	public int update(String sql, List<Object> params) throws SQLException {
		Connection con = getCon();
		PreparedStatement pstmt = null;
		int result = -1;
		try {
			pstmt = doPrepareStatement(sql, params, con);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			LogUtil.error(e);
			throw e;
		} finally {
			try {
				closeAll(null, pstmt, con);
			} catch (SQLException e) {
				LogUtil.error(e);
				throw e;
			}
		}
		return result;
	}

	public List<T> select(String sql, List<Object> params, Class<T> cls)
			throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		// 先调用下面的 select,得到List<Map>
		List<Map<String, String>> listMap = select(sql, params);
		List<T> listT = new ArrayList<T>();
		if (listMap != null && listMap.size() > 0) {
			for (Map<String, String> map : listMap) {
				// 将map转为 T对象
				T t = parseMapToT(map, cls);
				listT.add(t);
			}
		}
		return listT;
	}

	private T parseMapToT(Map<String, String> map, Class<T> cls)
			throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		T t = cls.newInstance(); // -> 会自动调用一个类中的无参构造方法. Type t=new Type();
		// 从cls中取出所有的的 setXXX方法
		List<Method> setMethods = findAllSetMethod(cls);
		for (Map.Entry<String, String> entry : map.entrySet()) {
			String key = entry.getKey(); // ID TNAME PID
			String value = entry.getValue(); // 1 军事家 0
			// 循环setMethods方法列表，依次找到 setId( int), setTname(String) setPid(int)
			for (Method m : setMethods) {
				// 每找到一个方法，则激活方法
				String methodName = "set" + key; // setID setTNAME setPIT
				if (methodName.equalsIgnoreCase(m.getName())) {
					// 是这个方法，则激活
					// 将value转类型
					// 查出这个m中的参数的类型
					Class c = m.getParameterTypes()[0];
					String parameterType = c.getName();
					if ("int".equals(parameterType) || "java.lang.Integer".equals(parameterType)) {
						int v = Integer.parseInt(value);
						m.invoke(t, v); // t.setId( 1);
					} else if ("float".equals(parameterType) || "java.lang.Float".equals(parameterType)) {
						float v = Float.parseFloat(value);
						m.invoke(t, v);
					} else if ("double".equals(parameterType) || "java.lang.Double".equals(parameterType)) {
						double v = Double.parseDouble(value);
						m.invoke(t, v);
					} else {
						m.invoke(t, value);
					}
				}
			}
		}
		return t;
	}

	/**
	 * 查找cls反射类中的所有的set方法
	 * 
	 * @param cls
	 * @return
	 */
	private List<Method> findAllSetMethod(Class<T> cls) {
		List<Method> list = new ArrayList<Method>();
		Method[] ms = cls.getMethods();
		for (Method m : ms) {
			if (m.getName().startsWith("set")) {
				list.add(m);
			}
		}
		return list;
	}

	public Long selectCount(String sql, List<Object> params) {
		Connection con = getCon();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Long num = 0L;
		try {
			pstmt = doPrepareStatement(sql, params, con);
			// 查询
			rs = pstmt.executeQuery();
			if (rs.next()) {
				num = rs.getLong(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return num;

	}

	public List<Map<String, String>> select(String sql, List<Object> params) {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Connection con = getCon();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = doPrepareStatement(sql, params, con);
			// 查询
			rs = pstmt.executeQuery();
			// 通过rs了解查询出来的结果有几个列组成, -> 结果集的元数据 metadata
			ResultSetMetaData rsmd = rs.getMetaData();
			// 取出结果集中的列名
			List<String> columnNames = new ArrayList<String>();
			// rsmd.getColumnCount() 结果集中列的数量
			for (int i = 0; i < rsmd.getColumnCount(); i++) {
				// 从rsmd中取出下标为 i 的列名
				String columnLabelName = rsmd.getColumnLabel(i + 1);
				columnNames.add(columnLabelName);
			}
			while (rs.next()) {
				Map<String, String> rowMap = new HashMap<String, String>();
				// 再循环 columnNames取各列列名
				for (String cn : columnNames) {
					String value = rs.getString(cn);
					rowMap.put(cn, value);
				}
				list.add(rowMap);
			}
		} catch (SQLException e) {
			LogUtil.error(e);
		} finally {
			try {
				closeAll(rs, pstmt, con);
			} catch (SQLException e) {
				LogUtil.error(e);
			}
		}
		return list;
	}

	private PreparedStatement doPrepareStatement(String sql, List<Object> params, Connection con) throws SQLException {
		PreparedStatement pstmt = null;
		pstmt = con.prepareStatement(sql);
		if (params != null && params.size() > 0) {
			for (int i = 0; i < params.size(); i++) {
				pstmt.setObject(i + 1, params.get(i));
			}
		}
		return pstmt;
	}

	private void closeAll(ResultSet rs, PreparedStatement pstmt, Connection con) throws SQLException {
		if (rs != null) {
			rs.close();
		}
		if (pstmt != null) {
			pstmt.close();
		}
		if (con != null) {
			con.close();
		}
	}

	public long selectFun(String sql, List<Object> params) throws SQLException {
		long result = 0;
		Connection con = getCon();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = doPrepareStatement(sql, params, con);
			// 查询
			rs = pstmt.executeQuery();
			if (rs.next()) {
				result = rs.getLong(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeAll(rs, pstmt, con);
		}
		return result;
	}

}
