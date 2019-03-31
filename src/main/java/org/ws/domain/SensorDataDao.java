package org.ws.domain;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ws.config.MongoCollectionMap;
import org.ws.entity.SensorDataModel;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.WriteResult;


@Component
public class SensorDataDao {

	Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private MongoCollectionMap cllecctionMap;

	public void saveSensorData(Long userid,SensorDataModel model) {
		DBCollection collection = cllecctionMap.getDBByUserId(userid);
		// 考虑情况 里面没有数据 里面数据不连续 里面数据连续但在该范围内 里面数据连续但不在该范围内
		
			BasicDBObject dbObject = new BasicDBObject();
			BasicDBObject sortO = new BasicDBObject();
			dbObject.put("sensor_id",model.getSensorid());
			sortO.put("basic_time", -1);
			DBCursor cursor = collection.find(dbObject).sort(sortO).limit(1);
			BasicDBObject next;
			if (cursor.count() == 0) {
				// 当mongo未查询到数据时 新建一个mongo集合 并将数据插入其中
				log.info("进入到了 创建集合 方法内 dto为 "+model);
				WriteResult sensorO = createSensorO(model,collection);
				//
			} else if (!inTimeRange(((Long) (next = (BasicDBObject) cursor.next()).get("basic_time")), model.getBasictime())) {
				// 当查询到数据单数据大于一个小时时 将会做 信息的总集工作 包括 该段时间的数据总量 数据的平均值 最大值 最小值 
				log.info("进入到了 为前集合做数据总结工作界面 创建新集合 方法内 dto为 "+model);
				@SuppressWarnings("unchecked")
				List<Double> datalist = (List<Double>)next.get("data");
				org.bson.types.ObjectId id = (org.bson.types.ObjectId)next.get("_id");
//				Double max = Collections.max(datalist);
//				Float min = Collections.min(datalist);
				double max = datalist.stream().filter(x -> x!=null).mapToDouble(Double::doubleValue).max().getAsDouble();
				double min = datalist.stream().filter(x -> x!=null).mapToDouble(Double::doubleValue).min().getAsDouble();
				double avg = datalist.stream().filter(x -> x!=null).mapToDouble(Double::doubleValue).average().getAsDouble();
				//并将其存储到集合中
				
				next.put("min", min);
				next.put("max", max);
				next.put("avg", avg);
//				collection.update(next, next);
				collection.update(new BasicDBObject("_id",id), new BasicDBObject("$set",next));
				// 新建一个mongo集合
				beforeCreateNewO();
				WriteResult sensorO = createSensorO(model,collection);
				
			} else {
//				log.info("进入到了 插入集合数据的方法内  dto为 "+model);
				// 该段数据仍处于分桶内 将其插入到该数据库下
				org.bson.types.ObjectId id = (org.bson.types.ObjectId)next.get("_id");
				
				@SuppressWarnings("unchecked")
				List<Double> datalist = (List<Double>)next.get("data");
				Integer length = (Integer)next.get("length");
//				next.remove("data");
//				next.remove("length");
				Long basicTime = (Long)next.get("basic_time");
				//临界条件 没想到有啥临界条件.....
//				if(time>1){
//					for(;time>1;time--){
//						datalist.add(null);
//					}
//				}
				
				datalist.add(model.getData());
				
				length=datalist.size();
//				next.append("data", datalist);
//				next.append("length", length);
//				collection.update(next,next);
				collection.update(new BasicDBObject("_id",id) , new BasicDBObject("$push", new BasicDBObject("data",model.getData())));
				collection.update(new BasicDBObject("_id",id) , new BasicDBObject("$set", new BasicDBObject("length",length)));
			}
	}
	private WriteResult createSensorO(SensorDataModel dto,DBCollection collection){
		BasicDBObject addDoc = new BasicDBObject("sensor_id", dto.getSensorid());
		addDoc.put("basic_time", dto.getBasictime()); 
		List<Double> datalist = new ArrayList<Double>();
		datalist.add(dto.getData());
		addDoc.put("data", datalist);
		addDoc.put("length",1);
		WriteResult result = collection.insert(addDoc);
		return result;
	}
	//将用于新建总集数据集合
	protected void beforeCreateNewO(){
		
	}
	
	/**
	 * 如果createTime在basic的一小时范围内 则返回true否则返回false
	 * @param baseTime
	 * @param createTime
	 * @return
	 */
	private boolean inTimeRange(Long baseTime,Long createTime){
		Timestamp baseT = new Timestamp(baseTime);
		Timestamp createT = new Timestamp(createTime);
		Calendar ca=Calendar.getInstance();
		ca.setTime(baseT);
		ca.add(Calendar.HOUR, 1);
		long timeInMillis = ca.getTimeInMillis();
		Timestamp timeAfterAHour = new Timestamp(timeInMillis);
		return timeAfterAHour.after(createT);
	}
}
