package com.ll.messages;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.PartitionInfo;

import kafka.utils.VerifiableProperties;

public class RoundRobinPartitioner implements Partitioner {
	
	//记录当前是第几条信息   注意多线程，原子锁
	private static AtomicLong next= new AtomicLong();
	
	public RoundRobinPartitioner() {
		System.out.println("RoundRobinPartitioner构造了。。。");
	}
	
	public RoundRobinPartitioner(VerifiableProperties verifiableProperties){
		System.out.println("RoundRobinPartitioner构造了。。。"+verifiableProperties);
	}

	@Override
	public void configure(Map<String, ?> configs) {
		System.out.println("RoundRobinPartition的configure方法，读取到的配置信息如下");
		System.out.println(configs.toString());
	}

	@Override
	public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
		System.out.println("调用partition");
		List<PartitionInfo> partitions= cluster.partitionsForTopic(topic);
		int numPartitions= partitions.size();
		System.out.println("配置的分区数："+numPartitions);
		long nextIndex= next.incrementAndGet();//自增，加锁
		
		int p= (int) (nextIndex % numPartitions);
		System.out.println("经过"+nextIndex+"计算后，将这条数据"+key+"："+key+"分到："+p+"分区上");
		return p;
	}

	@Override
	public void close() {
		System.out.println("关闭RoundRobinPartitioner类");
	}

}
