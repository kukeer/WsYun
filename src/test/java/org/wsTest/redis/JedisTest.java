package org.wsTest.redis;

import java.util.HashMap;
import java.util.Map;

import redis.clients.jedis.Jedis;

public class JedisTest {
	
	Jedis jedis;
	private final short REDUCE_NUMBER = 3;
	private final short SELECT_IGNOR_TIME=REDUCE_NUMBER + 1;
	private final short MAX_SURVICE_TIME = 30;
	
	private void init(){
		jedis = new Jedis("localhost",6379);
		jedis.connect();
	}
	
	public boolean containsWarnInfo(String userid){
		
		return jedis.hexists("warningInfo", userid);
		
	}
	

	public void setExpireTime(String name){
		if(!jedis.exists(name)){
			jedis.set(name, "");
		}
		
		jedis.expire(name, MAX_SURVICE_TIME);
	}
	
	
	
	public boolean refreshExpireTime(String key){
		if(!jedis.exists(key)){
			return false;
		}
		Long ttl = jedis.ttl(key);
		if(ttl<SELECT_IGNOR_TIME){
			return false;
		}
		jedis.expire(key, (int)(ttl-REDUCE_NUMBER));
		return true;
	}
}
