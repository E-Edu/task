package com.gewia.taskservice.pubsub.pub;

import com.gewia.taskservice.pubsub.PubSubService;
import java.util.Map;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.Topic;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
public class MessagePublisher {

	@Autowired private RedisTemplate<String, Object> redisTemplate;
	@Autowired private Map<Class<?>, Topic> topics;

	public void publish(Object message) {
		if (!PubSubService.isENABLED()) return;
		this.redisTemplate.convertAndSend(this.topics.get(message.getClass()).getTopic(), message);
	}

}
