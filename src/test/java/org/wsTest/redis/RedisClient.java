package org.wsTest.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

//@Configuration
//@PropertySource("classpath:redis.properties")
//@Slf4j
public class RedisClient {
//	   @Value("${spring.redis.host}")
//	    private String host;
//
//	    @Value("${spring.redis.port}")
//	    private int port;
//
//	    @Value("${spring.redis.timeout}")
//	    private int timeout;
//
//	    @Value("${spring.redis.jedis.pool.max-idle}")
//	    private int maxIdle;
//
//	    @Value("${spring.redis.jedis.pool.max-wait}")
//	    private long maxWaitMillis;
//	    
//	    @Value("${spring.redis.database}")
//	    private int database;
//	    
//	    @Value("${spring.redis.password}")
//	    private String password;
//
//	    @Value("${spring.redis.block-when-exhausted}")
//	    private boolean  blockWhenExhausted;
//	
//	    public JedisPool redisPoolFactory(){
//	    	JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
//	    	jedisPoolConfig.setMaxIdle(maxIdle);
//	    	jedisPoolConfig.setBlockWhenExhausted(blockWhenExhausted);
//	    	jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
//	    	jedisPoolConfig.setJmxEnabled(true);
//	    	
//			JedisPool jedisPool = new JedisPool(jedisPoolConfig, host, port, timeout, password, database);
//			return jedisPool;
//	    }
//
}
