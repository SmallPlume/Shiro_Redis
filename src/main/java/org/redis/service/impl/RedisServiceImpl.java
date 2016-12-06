package org.redis.service.impl;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Set;

import org.redis.service.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

public class RedisServiceImpl implements RedisService {
	
	private static final Logger log = LoggerFactory.getLogger(RedisServiceImpl.class);

	private static String redisCode = "utf-8";

	private RedisTemplate<Serializable, Object> redisTemplate;
	
	public RedisTemplate<Serializable, Object> getRedisTemplate() {
		return redisTemplate;
	}

	public void setRedisTemplate(RedisTemplate<Serializable, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	@SuppressWarnings("unchecked")
	@Override
	public long del(final String... keys) {
		return redisTemplate.execute(new RedisCallback() {
			public Long doInRedis(RedisConnection connection)
					throws DataAccessException {
				long result = 0;
				for (int i = 0; i < keys.length; i++) {
					result = connection.del(keys[i].getBytes());
				}
				return result;
			}
		});
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public long del(final byte[] key) {
		return redisTemplate.execute(new RedisCallback(){
			public Long doInRedis(RedisConnection connection)
					throws DataAccessException{
				long result = 0;
				result = connection.del(key);
				return result;
			}
		});
	}

	@SuppressWarnings("unchecked")
	@Override
	public void set(final byte[] key, final byte[] value, final long liveTime) {
		log.debug("将数据插入redis的key的值===========【{}】",new String(key));
		log.debug("将数据插入redis的value的值=========【{}】", new String(value));
		redisTemplate.execute(new RedisCallback() {
			public Long doInRedis(RedisConnection connection)
					throws DataAccessException {
				connection.set(key, value);
				if (liveTime > 0) {
					connection.expire(key, liveTime);
				}
				return 1L;
			}
		});
	}

	/**
	 * @param key
	 * @param value
	 * @param liveTime
	 */
	/*@Override
	public void set(String key, String value, long liveTime) {
		this.set(key.getBytes(), value.getBytes(), liveTime);
	}*/

	/**
	 * @param key
	 * @param value
	 */
	/*@Override
	public void set(String key, String value) {
		this.set(key, value, 0L);
	}*/

	/**
	 * @param key
	 * @param value
	 */
	@Override
	public void set(byte[] key, byte[] value) {
		this.set(key, value, 0L);
	}
	
	@Override
	public void Setkeys(String pattern) {
		 redisTemplate.keys(pattern);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public byte[] get(final byte[] key) {
		return redisTemplate.execute(new RedisCallback() {
			public byte[] doInRedis(RedisConnection connection)
					throws DataAccessException {
				return connection.get(key);
			}
		});
	}

	/**
	 * @param key
	 * @return
	 */
	/*@SuppressWarnings("unchecked")
	@Override
	public String get(final String key) {
		return redisTemplate.execute(new RedisCallback() {
			public String doInRedis(RedisConnection connection)
					throws DataAccessException {
				String value = null;
				value = connection.get(key.getBytes()).toString();
				return value;
			}
		});
	}*/
	
	@SuppressWarnings("unchecked")
	@Override
	public Set<byte[]> keys(final String pattern) {
		return redisTemplate.execute(new RedisCallback() {
			public Set<byte[]> doInRedis(RedisConnection connection)
					throws DataAccessException {
				Set<byte[]> keys = null;
				keys = connection.keys(pattern.getBytes());
				return  keys;
			}
		});
	}
	
	/**
	 * 判断是否存在
	 * @param key
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean exists(final String key) {
		return redisTemplate.execute(new RedisCallback() {
			public Boolean doInRedis(RedisConnection connection)
					throws DataAccessException {
				return connection.exists(key.getBytes());
			}
		});
	}

	/**
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String flushDB() {
		return redisTemplate.execute(new RedisCallback() {
			public String doInRedis(RedisConnection connection)
					throws DataAccessException {
				connection.flushDb();
				return "ok";
			}
		});
	}

	/**
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public long dbSize() {
		return redisTemplate.execute(new RedisCallback() {
			public Long doInRedis(RedisConnection connection)
					throws DataAccessException {
				return connection.dbSize();
			}
		});
	}

	/**
	 * @return
	 */ 
	@SuppressWarnings("unchecked")
	@Override
	public String ping() {
		return redisTemplate.execute(new RedisCallback() {
			public String doInRedis(RedisConnection connection)
					throws DataAccessException {

				return connection.ping();
			}
		});
	}

	private RedisServiceImpl(){}

}
