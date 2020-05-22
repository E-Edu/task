package com.gewia.taskservice.pubsub.sub.api;

import com.gewia.taskservice.pubsub.PubSubService;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.Topic;

/**
 * This class transforms the given {@link Message} to the requested object of type T.
 *
 * @param <T> The requested object type
 */
public interface ObjectSubscriber<T> extends Subscriber<Message> {

	RedisTemplate<String, Object> getRedisTemplate();

	RedisMessageListenerContainer getMessageListenerContainer();

	Map<Class<?>, Topic> getTopics();

	Class<T> getMessageClass();

	@PostConstruct
	default void registerSubscription() {
		if (!PubSubService.isENABLED()) return;
		this.getMessageListenerContainer().addMessageListener(this, this.getTopics().get(this.getMessageClass()));
	}

	@Override
	default void receive(Message message) {
		Object obj = this.getRedisTemplate().getValueSerializer().deserialize(message.getBody()); //TODO: Can we check the type before deserialization?
		if (!this.getMessageClass().isInstance(obj)) return;
		T dto = (T) obj;
		this.receive(dto);
	}

	void receive(T msg);

}
