package de.themorpheus.edu.taskservice.pubsub;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.data.redis.serializer.RedisSerializer;
import java.util.List;

@SuppressWarnings("checkstyle:illegalCatch")
public class LoggingRedisTemplate<K, V> extends RedisTemplate<K, V> {

	private static final Logger LOGGER = LoggerFactory.getLogger(LoggingRedisTemplate.class.getSimpleName());

	@Override
	public <T> T execute(RedisCallback<T> action, boolean exposeConnection, boolean pipeline) {
		try {
			return super.execute(action, exposeConnection, pipeline);
		} catch (Exception ex) {
			LOGGER.error("Error executing cache operation", ex);
			return null;
		}
	}


	@Override
	public <T> T execute(RedisScript<T> script, List<K> keys, Object... args) {
		try {
			return super.execute(script, keys, args);
		} catch (Exception ex) {
			LOGGER.error("Error executing cache operation", ex);
			return null;
		}
	}


	@Override
	public <T> T execute(RedisScript<T> script, RedisSerializer<?> argsSerializer, RedisSerializer<T> resultSerializer, List<K> keys, Object... args) {
		try {
			return super.execute(script, argsSerializer, resultSerializer, keys, args);
		} catch (Exception ex) {
			LOGGER.error("Error executing cache operation", ex);
			return null;
		}
	}


	@Override
	public <T> T execute(SessionCallback<T> session) {
		try {
			return super.execute(session);
		} catch (Exception ex) {
			LOGGER.error("Error executing cache operation", ex);
			return null;
		}
	}

}
