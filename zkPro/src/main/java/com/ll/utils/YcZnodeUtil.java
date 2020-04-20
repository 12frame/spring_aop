package com.ll.utils;

import java.text.SimpleDateFormat;

import org.apache.zookeeper.data.Stat;

/**
 * 工具类: 用于输出znode信息
 * 
 */
public class YcZnodeUtil {
	private static SimpleDateFormat df=new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");

	public static String printZnodeInfo(Stat stat) {
		StringBuffer sb = new StringBuffer();
		sb.append("\n*******************************\n");
		sb.append("创建znode的事务id czxid:"+stat.getCzxid()+"\n");
		sb.append("创建znode的时间 ctime:"+  df.format(   stat.getCtime()   )    +"\n");
		sb.append("更新znode的事务id mzxid:"+stat.getMzxid()+"\n");
		sb.append("更新znode的时间 mtime:"+df.format(   stat.getMtime()   )+"\n");
		sb.append("更新或删除本节点或子节点的事务id pzxid:"+stat.getPzxid()+"\n");
		sb.append("子节点数据更新次数 cversion:"+stat.getCversion()+"\n");
		sb.append("本节点数据更新次数 dataVersion:"+stat.getVersion()+"\n");
		sb.append("节点ACL(授权信息)的更新次数 aclVersion:"+stat.getAversion()+"\n");
		if(   stat.getEphemeralOwner()==0 ){
			sb.append("本节点为持久节点\n");
		}else{
			sb.append("本节点为临时节点,创建客户端id为:"+ stat.getEphemeralOwner()+"\n");
		}
		sb.append("数据长度为:"+stat.getDataLength()+"字节\n");
		sb.append("子节点个数:"+stat.getNumChildren()+"\n");
		sb.append("\n*******************************\n");
		return sb.toString();
	}

}
