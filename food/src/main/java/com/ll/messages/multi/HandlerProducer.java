package com.ll.messages.multi;

import org.apache.log4j.Logger;

import com.ll.messages.MailInfo;

/**
* @author 张影 QQ:1069595532  WX:zycqzrx1
* @date Mar 6, 2020
*/
public class HandlerProducer implements Runnable { 
	private String topic;
	private int retry;
    private MailInfo message; 
    
    private Logger logger= Logger.getLogger(HandlerProducer.class);
    
    public HandlerProducer(String topic, int retry, MailInfo message) {  
    	this.topic=topic;
    	this.retry=retry;
        this.message = message;  
    }  
  
    @Override  
    public void run() {  
    	//调用单例类，只生成唯一的一个  producer实例
        KafkaProducerSingleton kafkaProducerSingleton = KafkaProducerSingleton.getInstance( retry );  
        //多实例，就在这里new
        logger.info("当前线程:" + Thread.currentThread().getName()   + ",获取的kafka实例:" + kafkaProducerSingleton);  
        kafkaProducerSingleton.sendKafkaMessage( topic,  message);
        System.out.println("00");
    }  
  
} 
