package com.gewia.taskservice.pubsub.sub.api;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

public interface Subscriber<T extends Message> extends MessageListener {

	@Override
	default void onMessage(Message message, byte[] pattern) {
		this.receive((T) message);
	}

	void receive(T t);

}
