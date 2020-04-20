package com.ll.messages;

import java.io.IOException;

import org.apache.kafka.common.serialization.Deserializer;

/**
 * 利用protostosfull完成反序列化
 * @author root
 *
 */
public class MailInfoProtostuffDeserializer implements Deserializer<MailInfo> {

	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public MailInfo deserialize(String topic, byte[] data) {
		return ProtostuffUtils.deserialize(data, MailInfo.class);
	}

}
