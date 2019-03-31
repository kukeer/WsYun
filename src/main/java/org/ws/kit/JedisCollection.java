package org.ws.kit;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Component
public class JedisCollection {

	JedisPool jedisPool;

	private int PORT = 6379;

	private String ADDR = "127.0.0.1";

	private int MAX_ACTIVE = 1024;

	private int MAX_IDLE = 200;

	private int MAX_WAIT = 10000;

	private int TIMEOUT = 10000;

	private boolean TEST_ON_BORROW = true;

	private final short REDUCE_NUMBER = 3;
	private final short SELECT_IGNOR_TIME = REDUCE_NUMBER + 1;
	private final short MAX_SURVICE_TIME = 30;

	private Map<Long, Boolean> keymap = new HashMap<Long, Boolean>();

	public JedisCollection() {
		init();
	}

	private void init() {
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxIdle(MAX_IDLE);
		config.setMaxWaitMillis(MAX_WAIT);
		config.setTestOnBorrow(TEST_ON_BORROW);
		jedisPool = new JedisPool(config, ADDR, PORT, TIMEOUT);
	}

	private Jedis getJedis() {
		try {
			if (jedisPool != null) {
				Jedis resource = jedisPool.getResource();
				return resource;
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private void returnResource(final Jedis jedis) {
		if (jedis != null) {
			jedisPool.returnResource(jedis);
		}
	}

	public boolean containsWarnInfo(String userid) {
		Jedis jedis = null;
		Boolean hexists = null;
		try {
			jedis = getJedis();
			if (jedis.exists(userid) == null) {
				return false;
			}
			hexists = jedis.exists(userid);
			
		} catch (Exception e) {
			returnResource(jedis);
		}finally{
			returnResource(jedis);
		}

		return hexists;

	}

	public void setExpireTime(String name) {
		Jedis jedis = null;
		Boolean hexists = null;
		try {
			jedis = getJedis();
			if (!jedis.exists(name)) {
				jedis.set(name, "");
			}

			jedis.expire(name, MAX_SURVICE_TIME);
			
		} catch (Exception e) {
			returnResource(jedis);
		}finally{
			returnResource(jedis);
		}
	}

	public boolean refreshExpireTime(String key) {
		Jedis jedis = null;
		Boolean hexists = null;
		try {
			jedis = getJedis();
			if (!jedis.exists(key)) {
				return false;
			}
			Long ttl = jedis.ttl(key);
			if (ttl < SELECT_IGNOR_TIME) {
				return false;
			}
			jedis.expire(key, (int) (ttl - REDUCE_NUMBER));
			
		} catch (Exception e) {
			returnResource(jedis);
		}finally{
			returnResource(jedis);
		}
		return true;
	}
}
