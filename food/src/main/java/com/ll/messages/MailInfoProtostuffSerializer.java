package com.ll.messages;

import org.apache.kafka.common.serialization.Serializer;

public class MailInfoProtostuffSerializer implements Serializer<MailInfo> {

	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public byte[] serialize(String topic, MailInfo data) {
		return ProtostuffUtils.serialize(data);
	}

}
