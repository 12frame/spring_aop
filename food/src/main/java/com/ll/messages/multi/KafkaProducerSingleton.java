package com.ll.messages.multi;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ll.messages.MailInfo;

/**
 * kafka生产者的单例类
 * 生产者KafkaProducer是线程安全对象，所以我们建议KafkaProducer采用单例模式,多个线程共享一个实例
 * 
 */
public final class KafkaProducerSingleton {
	private static final Logger logger = LoggerFactory.getLogger(KafkaProducerSingleton.class);
	private static KafkaProducer<Integer, MailInfo> kafkaProducer;
	private int retry; // 重连次数
	private static KafkaProducerSingleton instance;   //唯一的一个实例

	// 单例要求构造方法私有化
	private KafkaProducerSingleton(int retry) {
		this.retry = retry;
		if (null == kafkaProducer) {
			Properties props = new Properties();
			InputStream inStream = null;
			logger.error("kafkaProducer开始初始化");
			try {
				logger.error("kafkaProducer初始化成功");
				inStream = this.getClass().getClassLoader().getResourceAsStream("kafka.properties");
				props.load(inStream);
				kafkaProducer = new KafkaProducer<Integer, MailInfo>(props);
			} catch (IOException e) {
				logger.error("kafkaProducer初始化失败:" + e.getMessage(), e);
			} finally {
				if (null != inStream) {
					try {
						inStream.close();
					} catch (IOException e) {
						logger.error("kafkaProducer初始化失败:" + e.getMessage(), e);
					}
				}
			}
		}
	}

	/**
	 * 单例模式,kafkaProducer是线程安全的,可以多线程共享一个实例
	 * @return
	 */
	public static final KafkaProducerSingleton getInstance(int retry) {
		if (instance == null) {
			instance = new KafkaProducerSingleton(retry);
		}
		return instance;
	}

	/**
	 * 通过kafkaProducer发送消息
	 * 
	 * @param topic 消息接收主题
	 * @param partitionNum 哪一个分区
	 * @param retry  重试次数
	 * @param message  具体消息值
	 */
	public void sendKafkaMessage(String topic, final MailInfo message) {
		/**
		 * 1、如果指定了某个分区,会只讲消息发到这个分区上 2、如果同时指定了某个分区和key,则也会将消息发送到指定分区上,key不起作用
		 * 3、如果没有指定分区和key,那么将会随机发送到topic的分区中 4、如果指定了key,那么将会以hash<key>的方式发送到分区中
		 */
		ProducerRecord<Integer, MailInfo> record = new ProducerRecord<Integer, MailInfo>( topic, message);
		System.out.println("开始发送消息");
		// send方法是异步的,添加消息到缓存区等待发送,并立即返回，这使生产者通过批量发送消息来提高效率
		// kafka生产者是线程安全的,可以单实例发送消息
		kafkaProducer.send(record, new Callback() {
			public void onCompletion(RecordMetadata metadata, Exception exception) {
				if (null != exception) {
					logger.error("kafka发送消息失败:" + exception.getMessage(), exception);
					System.out.println(exception.getMessage());
					retryKakfaMessage(topic,message);
				}else{
					System.out.println(Thread.currentThread().getName()+"发送了消息成功:TopicName : " + metadata.topic() + " Partiton : " + metadata.partition() + " Offset : "
							+ metadata.offset()+",数据的校验和: "+metadata.checksum()+"时间戳:"+ new Date( metadata.timestamp() ) );
			
				}
			}
		});
	}

	/**
	 * 当kafka消息发送失败后,重试
	 * 
	 * @param retryMessage
	 */
	private void retryKakfaMessage(String topic ,final MailInfo retryMessage) {
		ProducerRecord<Integer, MailInfo> record = new ProducerRecord<Integer, MailInfo>(topic, retryMessage);
		for (int i = 1; i <= retry; i++) {
			try {
				kafkaProducer.send(record);
				return;
			} catch (Exception e) {
				logger.error("kafka发送第"+i+"次消息"+   "键为:"+topic+"失败:" + e.getMessage(), e);
			}
		}
	}

	/**
	 * kafka实例销毁
	 */
	public void close() {
		if (null != kafkaProducer) {
			kafkaProducer.close();
		}
	}

	public int getRetry() {
		return retry;
	}

	public void setRetry(int retry) {
		this.retry = retry;
	}

}
