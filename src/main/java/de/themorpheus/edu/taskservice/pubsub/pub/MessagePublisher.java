package de.themorpheus.edu.taskservice.pubsub.pub;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.Topic;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
@NoArgsConstructor
public class MessagePublisher {

	@Autowired private RedisTemplate<String, Object> redisTemplate;
	@Autowired private Map<Class<?>, Topic> topics;

	public void publish(Object message) {
		redisTemplate.convertAndSend(topics.get(message.getClass()).getTopic(), message);
	}

}
