package com.ll;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;
import java.util.Scanner;

import org.apache.log4j.Logger;
import org.apache.zookeeper.ZooKeeper;

public class DataNoteServer {
	
	private String parentNode= "/servers";//这是整个集群的根节点
	private static ZkHelper zkHelper;
	private static ZooKeeper zk;
	private static Logger logger= Logger.getLogger(DataNoteServer.class);
	private static Scanner sc= new Scanner(System.in);

	/**
	 * 客户端与服务器连接
	 * @throws InterruptedException 
	 * @throws IOException 
	 */
	private void getConnect() throws IOException, InterruptedException {
		zkHelper= new ZkHelper();
		zk= zkHelper.connect();
	}
	
	/**
	 * 将对象转化为byte[]
	 * @param obj
	 * @return
	 */
	private byte[] toByteArray(Object obj){
		byte[] bt= null;
		return bt;
	}
	
	/**
	 * 将客户端的数据注册到zk中
	 */
	private void registerServer(){}
	
	/**
	 * 业务方法
	 */
	private void business(){
		System.out.println("请输入割圆次数：");
		int n = sc.nextInt();
		double y = 1.0;
		for (int i = 0; i <= n; i++) {
			double π = 3 * Math.pow(2, i) * y;
			System.out.println("第" + i + "次切割,为正" + (6 + 6 * i) + "边形，圆周率π≈" + π);
			y = Math.sqrt(2 - Math.sqrt(4 - y * y));
		}
	}

	private static Properties getLocalHostInfo() {
		Runtime r = Runtime.getRuntime();  //jvm信息  : jvm内存 
		Properties props = System.getProperties();    //操作系统信息
		InetAddress addr=null;
		try {
			addr = InetAddress.getLocalHost();   // ip地址
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		String ip = addr.getHostAddress();      //取地址
		
		props.setProperty("ip", ip);
		props.setProperty("hostname", addr.getHostName());
		props.setProperty("totalMem", r.totalMemory() + "");
		props.setProperty("freeMemory", r.freeMemory() + "");
		props.setProperty("availableProcessors", r.availableProcessors() + "");

		System.out.println("ip:    " + ip);
		System.out.println("hostname:    " + addr.getHostName());
		System.out.println("JVM可以使用的总内存:    " + r.totalMemory());
		System.out.println("JVM可以使用的剩余内存:    " + r.freeMemory());
		System.out.println("JVM可以使用的处理器个数:    " + r.availableProcessors());
		System.out.println("Java的运行环境版本：    " + props.getProperty("java.version"));
		System.out.println("Java的运行环境供应商：    " + props.getProperty("java.vendor"));
		System.out.println("Java供应商的URL：    " + props.getProperty("java.vendor.url"));
		System.out.println("Java的安装路径：    " + props.getProperty("java.home"));
		System.out.println("Java的虚拟机规范版本：    " + props.getProperty("java.vm.specification.version"));
		System.out.println("Java的虚拟机规范供应商：    " + props.getProperty("java.vm.specification.vendor"));
		System.out.println("Java的虚拟机规范名称：    " + props.getProperty("java.vm.specification.name"));
		System.out.println("Java的虚拟机实现版本：    " + props.getProperty("java.vm.version"));
		System.out.println("Java的虚拟机实现供应商：    " + props.getProperty("java.vm.vendor"));
		System.out.println("Java的虚拟机实现名称：    " + props.getProperty("java.vm.name"));
		System.out.println("Java运行时环境规范版本：    " + props.getProperty("java.specification.version"));
		System.out.println("Java运行时环境规范供应商：    " + props.getProperty("java.specification.vender"));
		System.out.println("Java运行时环境规范名称：    " + props.getProperty("java.specification.name"));
		System.out.println("Java的类格式版本号：    " + props.getProperty("java.class.version"));
		System.out.println("Java的类路径：    " + props.getProperty("java.class.path"));
		System.out.println("加载库时搜索的路径列表：    " + props.getProperty("java.library.path"));
		System.out.println("默认的临时文件路径：    " + props.getProperty("java.io.tmpdir"));
		System.out.println("一个或多个扩展目录的路径：    " + props.getProperty("java.ext.dirs"));
		System.out.println("操作系统的名称：    " + props.getProperty("os.name"));
		System.out.println("操作系统的构架：    " + props.getProperty("os.arch"));
		System.out.println("操作系统的版本：    " + props.getProperty("os.version"));
		System.out.println("文件分隔符：    " + props.getProperty("file.separator"));
		System.out.println("路径分隔符：    " + props.getProperty("path.separator"));
		System.out.println("行分隔符：    " + props.getProperty("line.separator"));
		System.out.println("用户的账户名称：    " + props.getProperty("user.name"));
		System.out.println("用户的主目录：    " + props.getProperty("user.home"));
		System.out.println("用户的当前工作目录：    " + props.getProperty("user.dir"));
		
		return props;
	}

	public static void main(String[] args) throws IOException, InterruptedException {
		DataNoteServer server= new DataNoteServer();
		server.getConnect();
		
		boolean flag= false;
		int choice= 2;
		while(!flag){
			System.out.println("请输入你要做的操作：\n1.执行计算任务\n2.退相互系统");
			choice= sc.nextInt();
			switch(choice){
			case 1:
				server.business();
				break;
			case 2:
				zkHelper.close();
				flag= true;
				sc.close();
				break;
			default:
					System.out.println("没有这个操作...");
			}
		}
		System.out.println("客户端退出");
	}
}
