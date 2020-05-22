package com.gewia.taskservice.pubsub;

import com.gewia.taskservice.pubsub.dto.PubSubDTO;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.Topic;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.stereotype.Service;

@Service
public class PubSubService {

	@Getter
	@Value("${spring.redis.enabled}")
	private static boolean ENABLED;

	private static final Logger LOGGER = LoggerFactory.getLogger(PubSubService.class.getSimpleName());

	@Value("${spring.redis.host}")
	private String redisHost;
	@Value("${spring.redis.port}")
	private int redisPort;

	@Bean
	public Map<Class<?>, Topic> topics() throws ClassNotFoundException {
		Map<Class<?>, Topic> topics = new HashMap<>();

		ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
		provider.addIncludeFilter(new AnnotationTypeFilter(PubSubDTO.class));

		for (BeanDefinition beanDefinition : provider.findCandidateComponents("de.themorpheus")) {
			Class<?> clazz = Class.forName(beanDefinition.getBeanClassName());
			PubSubDTO pubSubDTO = clazz.getAnnotation(PubSubDTO.class);

			topics.put(clazz, new PatternTopic(pubSubDTO.value()));
		}

		return topics;
	}

	@Bean
	public JedisConnectionFactory jedisConnectionFactory() {
		JedisConnectionFactory factory = new JedisConnectionFactory();

		factory.getStandaloneConfiguration().setHostName(this.redisHost);
		factory.getStandaloneConfiguration().setPort(this.redisPort);

		return factory;
	}

	@Bean
	public RedisMessageListenerContainer redisContainer() {
		RedisMessageListenerContainer container = new RedisMessageListenerContainer();

		container.setConnectionFactory(this.jedisConnectionFactory());
		container.setErrorHandler(t -> LOGGER.error("Redis exceptions", t));

		return container;
	}

	@Bean
	public RedisTemplate<String, Object> redisTemplate() {
		LoggingRedisTemplate<String, Object> template = new LoggingRedisTemplate<>();
		GenericJackson2JsonRedisSerializer serializer = new GenericJackson2JsonRedisSerializer();

		template.setConnectionFactory(this.jedisConnectionFactory());
		template.setKeySerializer(serializer);
		template.setHashValueSerializer(serializer);
		template.setValueSerializer(serializer);

		return template;
	}

}
