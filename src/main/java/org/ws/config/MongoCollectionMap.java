package org.ws.config;

import java.util.TreeMap;

import org.springframework.stereotype.Component;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
@Component
public class MongoCollectionMap {
	TreeMap<Long, DBCollection> dbMap;
	public MongoCollectionMap() {
		dbMap = new TreeMap<Long, DBCollection>();
	}
	
	public boolean putDBInfo(Long userId,DBCollection db){
		if(dbMap.get(userId)==null){
			dbMap.put(userId, db);
			return true;
		}
		return false;
	}
	
	public DBCollection getDBByUserId(Long userId){
		DBCollection collection = dbMap.get(userId);
		if(collection==null){
//			throw new NullPointerException("键为"+userId+"的DB不存在");
			MongoClient mongoClient = new MongoClient();
			DB db1 = mongoClient.getDB("sensor");
			DBCollection collection1 = db1.getCollection(""+userId);
			dbMap.put(userId, collection1);
			return collection1;
			
		}
		return collection;
	}
	
	public DBCollection reNewDBCollection(Long userId){
		MongoClient mongoClient = new MongoClient();
		DB db1 = mongoClient.getDB("sensor");
		DBCollection collection1 = db1.getCollection(""+userId);
		dbMap.put(userId, collection1);
		return collection1;
	}
}
