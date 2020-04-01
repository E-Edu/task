package de.themorpheus.edu.taskservice.pubsub.sub.api;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.Topic;
import org.springframework.stereotype.Component;
import java.util.Map;

@Component
@NoArgsConstructor
public abstract class AbstractObjectSubscriber<T> implements ObjectSubscriber<T> {

	@Autowired @Getter private RedisTemplate<String, Object> redisTemplate;
	@Autowired @Getter private RedisMessageListenerContainer messageListenerContainer;
	@Autowired @Getter private Map<Class<?>, Topic> topics;

	@Getter private Class<T> messageClass;

	protected AbstractObjectSubscriber(Class<T> messageClass) {
		this.messageClass = messageClass;
	}

}
