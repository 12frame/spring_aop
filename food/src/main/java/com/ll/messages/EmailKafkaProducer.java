package com.ll.messages;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.log4j.Logger;

public class EmailKafkaProducer {

	private static final Properties props= new Properties();
	private static Logger logger= Logger.getLogger(EmailKafkaProducer.class);
	
	static{
		InputStream iis = EmailKafkaProducer.class.getClassLoader().getResourceAsStream("kafka.properties");
		try {
			props.load(iis);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				iis.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 1.启动zk: /bin/zookeeper-server-start.sh config/zooleeper.properties &
	 * 2.启动node1,node2,node3的kafka: /bin/kafka-server-start.sh config/server.properties &
	 * 在kafka中创建一个主题，名字为：mails
	 * bin/kafka-topics.sh --create --zookeeper node1:2181,node2:2181,node3:2181 --replication-factor 3 --partitions 3 --topic mails

	 * 查看mails详情
	 * bin/kafka-topics.sh --describe --zookeeper node1,2181,node2,2181,,node3,2181 --topic mails

	 * 运行   producer 生成消息
	 * 
	 * 查看mails每个消息的分区主题数：bin/kafka-run-class.sh kafka.tools.GetOffsetShell --broker-list node1:9092,node2:9092,node3:9092 --topic mails

	 * 开一个消费者读取消息： bin/kafka-console-consumer.sh --bootstrap-server node1:9092,node2:9092,node3:9092 --topic mails

	 * @param mailInfo
	 */
	public static void producer(MailInfo mailInfo){
		KafkaProducer<Integer, MailInfo> producer= new KafkaProducer<Integer, MailInfo>(props);
		ProducerRecord<Integer, MailInfo> pr= new ProducerRecord<Integer, MailInfo>(props.getProperty("mails.topic"), mailInfo);
		producer.send(pr);
		producer.close();
		logger.info("发送邮件消息到kafka"+mailInfo.getTo()+"完毕");
	}
	
}
