package com.ll;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;

import org.apache.log4j.Logger;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.ZooKeeper.States;
import org.apache.zookeeper.data.Stat;

import com.ll.utils.YcZnodeUtil;

public class NameNodeServer {
	
	private static ZkHelper zkHelper;
	private static ZooKeeper zk;
	private static Logger logger= Logger.getLogger(NameNodeServer.class);
	private String parentNode= "/servers";
	
	private void closeZookeeper(){
		logger.info("关闭zookeeper连接...");
		if (zk!=null&&zk.getState()==States.CONNECTED){
			try {
				zk.close();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void getServerList() throws KeeperException, InterruptedException{
		CountDownLatch connectedSignal= new CountDownLatch(Integer.MAX_VALUE);
		MyWatch mw= new MyWatch(zk,parentNode,connectedSignal);
		
		List<String> servers= zk.getChildren(parentNode, mw);
		logger.info("主程序启动，得到当前datanode列表：");
		for (String child:servers){
			byte[] data= zk.getData(parentNode+"/", false, null);//这个data就是客户端存入的  properties对象
			logger.info(child);
		}
		connectedSignal.wait();
	}
	
	private void initMainNode() throws Exception{
		zkHelper= new ZkHelper();
		zk= zkHelper.connect();
		if (zk == null || zk.getState() != States.CONNECTED){
			logger.error("没有建立起与zookeeper服务器"+zkHelper.getConnectString()+"连接");
			throw new Exception("没有建立起与zookeeper服务器"+zkHelper.getConnectString()+"连接");
		}
		try {
			zk.exists(parentNode, true);
		} catch (Exception e) {
			zk.create(parentNode, "this is yc datanode cluster".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		}
	}

	public static void main(String[] args) throws Exception {
		//1.实例化服务器
		NameNodeServer server= new NameNodeServer();
		//2.建立连接 namenodeserver->zk
		//3.判断/servers是否存在，如不存在，则创建。->init
		server.initMainNode();
		//4.绑定监听  /servers/下的子节点的变化
		//ls path [watch] -> getChildren
		//创建，删除子节点事件起作用
		//countDownLatch
		server.getServerList();
		//5.关闭服务器，释放资源
		server.closeZookeeper();
	}
}

class MyWatch implements Watcher{
	private ZooKeeper zk;
	private String path;
	private CountDownLatch connectedSignal;
	private Logger logger= Logger.getLogger(MyWatch.class);
	
	private void showChildNodeInfo(List<String> children){
		logger.info("当前在线的datanode有："+children.size());
		logger.info("他们是：");
		logger.info(children);
		logger.info("各节点的详情:");
		for (String sonPath:children){
			try {
				logger.info("*****"+sonPath+"********");
				byte[] data= zk.getData(path+"/", false, null);
				Properties p= (Properties) deserilizable(data);
				logger.info(p);
				logger.info("****************");
			} catch (KeeperException | InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private Object deserilizable(byte[] data){
		Object obj= null;
		ByteArrayInputStream bis= null;
		ObjectInputStream ois= null;
		try {
			bis= new ByteArrayInputStream(data);
			ois= new ObjectInputStream(bis);
			obj= ois.readObject();
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}finally{
			if (ois!=null){
				try {
					ois.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (bis!=null){
				try {
					bis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return obj;
	}

	@Override
	public void process(WatchedEvent event) {
		if ( event.getType()== Watcher.Event.EventType.NodeChildrenChanged){
			logger.info("监听到子节点的变化...");
			try {
				List<String> children= zk.getChildren(path, MyWatch.this);
				showChildNodeInfo(children);
			} catch (KeeperException | InterruptedException e) {
				e.printStackTrace();
				logger.error(e.getMessage());
			}
		}else if (event.getType()==Watcher.Event.EventType.NodeDataChanged){
			try {
				Stat stat= new Stat();
				
				byte[] data= zk.getData(path, MyWatch.this, stat);
				String dataString= new String(data,"utf-8");
				logger.info("监听程序中获取节点："+path+"的更新后的数据为："+dataString);
				logger.info("节点最新信息stat为：");
				String info=YcZnodeUtil.printZnodeInfo(stat);
				logger.info(info);
			} catch (Exception e) {
				e.printStackTrace();
			}
				
		}
		connectedSignal.countDown();
		if (connectedSignal.getCount()==1){
			connectedSignal= new CountDownLatch(Integer.MAX_VALUE);
		}
		System.out.println("当前connectedSignal为："+connectedSignal.getCount());
	}
	
	public MyWatch(ZooKeeper zk,String path,CountDownLatch connectedSignal){
		super();
		this.zk= zk;
		this.path= path;
		this.connectedSignal= connectedSignal;
	}
	
	public MyWatch(){
		super();
	}
	
}
