package de.themorpheus.edu.taskservice.pubsub;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.data.redis.serializer.RedisSerializer;

@SuppressWarnings("checkstyle:illegalCatch")
public class LoggingRedisTemplate<K, V> extends RedisTemplate<K, V> {

	private static final String EXECUTING_CACHE_OPERATION_ERROR = "Error executing cache operation";
	private static final Logger LOGGER = LoggerFactory.getLogger(LoggingRedisTemplate.class.getSimpleName());

	@Override
	public <T> T execute(RedisCallback<T> action, boolean exposeConnection, boolean pipeline) {
		try {
			return super.execute(action, exposeConnection, pipeline);
		} catch (Exception ex) {
			LOGGER.error(EXECUTING_CACHE_OPERATION_ERROR, ex);
			return null;
		}
	}


	@Override
	public <T> T execute(RedisScript<T> script, List<K> keys, Object... args) {
		try {
			return super.execute(script, keys, args);
		} catch (Exception ex) {
			LOGGER.error(EXECUTING_CACHE_OPERATION_ERROR, ex);
			return null;
		}
	}


	@Override
	public <T> T execute(RedisScript<T> script, RedisSerializer<?> argsSerializer, RedisSerializer<T> resultSerializer, List<K> keys, Object... args) {
		try {
			return super.execute(script, argsSerializer, resultSerializer, keys, args);
		} catch (Exception ex) {
			LOGGER.error(EXECUTING_CACHE_OPERATION_ERROR, ex);
			return null;
		}
	}


	@Override
	public <T> T execute(SessionCallback<T> session) {
		try {
			return super.execute(session);
		} catch (Exception ex) {
			LOGGER.error(EXECUTING_CACHE_OPERATION_ERROR, ex);
			return null;
		}
	}

}
