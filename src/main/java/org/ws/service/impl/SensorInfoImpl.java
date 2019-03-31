package org.ws.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ws.converter.InitSensorConverter;
import org.ws.domain.SensorDao;
import org.ws.domain.SensorDataDao;
import org.ws.dto.SensorDataDTO;
import org.ws.dto.SensorInfoDTO;
import org.ws.dto.TopicInfoDTO;
import org.ws.entity.SensorDataModel;
import org.ws.entity.SensorInfoModel;
import org.ws.kit.IMqttSender;
import org.ws.kit.JedisCollection;
import org.ws.kit.SnowFlake;
import org.ws.service.SensorInfoService;
import org.ws.service.UserService;

import com.google.gson.Gson;

import net.sf.json.JSONObject;

@Service
public class SensorInfoImpl implements SensorInfoService {

	Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	InitSensorConverter converter;

	@Autowired
	SnowFlake flake;

	@Autowired
	SensorDao dao;

	@Autowired
	UserService service;

	@Autowired
	SensorDataDao sensorDataDao;

	@Autowired
	IMqttSender sender;
//	@Autowired
//	MqqtServer mqttServer;
	
	@Autowired
	JedisCollection jedisCollection;

	@Override
	public void putInfo2Data(Long userId, JSONObject obj) {
		// TODO Auto-generated method stub
		Iterator keys = obj.keys();
		while (keys.hasNext()) {
			String next = (String) keys.next();
			JSONObject sensor = obj.getJSONObject(next);
			SensorInfoDTO sensorInfoDTO = new SensorInfoDTO();
			Gson gson = new Gson();
			SensorInfoDTO dto = gson.fromJson(sensor.toString(), SensorInfoDTO.class);
			SensorInfoModel bean = converter.toBean(dto);
			bean.setSensorid(flake.nextId());
			bean.setUser_id(userId);
			dao.saveSensorInfo(bean);
			service.initUserInfo(userId);
		}
	}

	@Override
	public void putsensor2Mongo(Long userId, JSONObject obj,String username) {
		beforePutsensor2Mongo(userId, obj, username);
	}

	/**
	 * 在數據插入前 應該做数据的校验工作 以及警报deng工作
	 * 
	 * @param userId
	 * @param obj
	 */
	private void beforePutsensor2Mongo(Long userId, JSONObject obj,String username) {
		List<SensorDataDTO> sensorList = parseSensorData(obj);
		for (SensorDataDTO sensorData : sensorList) {
			String name = sensorData.getName();
			SensorInfoModel sensorInfoModel = dao.findSensorBySensorNameAndUserId(name, userId);
			if (sensorInfoModel == null) {
				// 做返回操作
				return;
			}
			SensorDataModel sensorDataModel = new SensorDataModel();
			sensorDataModel.setBasictime(sensorData.getTime());
			sensorDataModel.setSensorid(sensorInfoModel.getSensorid());
			sensorDataModel.setData(sensorData.getData());
			Double data_max = sensorInfoModel.getData_max();

			Double data_min = sensorInfoModel.getData_min();
			// 如果存在collection存在该数据 则减少该数据的存活时间
			if (data_max != null || data_min != null) {

				if (sensorData.getData() > sensorInfoModel.getData_max()
						|| sensorData.getData() < sensorInfoModel.getData_min()) {
					// 触发警报系统 发送消息(待完成)
					if(!jedisCollection.containsWarnInfo(username)){
						//作如果初次设置 则向客户端传递消息
						TopicInfoDTO topicInfoDTO = new TopicInfoDTO();
						topicInfoDTO.setCreatedate(new Timestamp(sensorData.getTime()));
						topicInfoDTO.setMaxSecureData(sensorInfoModel.getData_max());
						topicInfoDTO.setMinSecureData(sensorInfoModel.getData_min());
						topicInfoDTO.setSensorId(sensorInfoModel.getSensorid());
						topicInfoDTO.setSensorname(sensorInfoModel.getSensor_name());
						topicInfoDTO.setUnit(sensorInfoModel.getSensor_unit());
						topicInfoDTO.setTopic("警告 您的传感器发生异常");
						topicInfoDTO.setWarnData(sensorData.getData());
						Gson gson = new Gson();
						String json = gson.toJson(topicInfoDTO);
						log.info("警告 您的传感器发生异常"+json);
						sender.sendToMqtt(username, 2, json);
//						try {
////							mqttServer.publish(username, json);
//						} catch (MqttException e) {
//							e.printStackTrace();
//						}
						
					}
					jedisCollection.setExpireTime(username);
					
				} else {
					if (jedisCollection.containsWarnInfo(username)) {
						jedisCollection.refreshExpireTime(username);
					}
				}
			}
			// 插入mongo数据库
			sensorDataDao.saveSensorData(userId, sensorDataModel);
			// 做返回操作
		}

	}

	/**
	 * 解析sensorData数据
	 * 
	 * @param obj
	 */
	private List<SensorDataDTO> parseSensorData(JSONObject obj) {
		Gson gson = new Gson();
		List<SensorDataDTO> list = new ArrayList<SensorDataDTO>();
		Iterator keys = obj.keys();
		while (keys.hasNext()) {
			JSONObject object = obj.getJSONObject((String) keys.next());
			SensorDataDTO fromJson = gson.fromJson(object.toString(), SensorDataDTO.class);
			list.add(fromJson);
		}
		return list;
	}

	@Override
	public void changeSensorRange(Long userId, JSONObject obj) {
		if (!obj.containsKey("sensorname")) {
			throw new IllegalArgumentException("没有传输传感器名称");
		}

		SensorInfoModel model = dao.findSensorBySensorNameAndUserId(obj.getString("sensorname"), userId);
		if (model == null)
			throw new IllegalArgumentException("用户不存在该传感器");

		if (obj.containsKey("min") && obj.containsKey("max")) {
			dao.updateSensorWarnRange(userId, obj.getString("sensorname"), obj.getDouble("min"), obj.getDouble("max"));
			return;
		} else if (obj.containsKey("min")) {
			dao.updateSensorWarnRangeMin(userId, obj.getString("sensorname"), obj.getDouble("min"));
		} else {
			dao.updateSensorWarnRangeMax(userId, obj.getString("sensorname"), obj.getDouble("max"));
		}
	}

	@Override
	public List<SensorInfoDTO> getSensorList(Long userid) {
		List<SensorInfoModel> list = dao.findSensorListBySensorNameAndUserId(userid);
		List<SensorInfoDTO> list1 = new ArrayList<SensorInfoDTO>();
		for (SensorInfoModel model : list) {
			SensorInfoDTO bean = converter.fromBean(model);
			list1.add(bean);

		}
		// Gson gson = new Gson();
		// String json = gson.toJson(list1);
		return list1;
	}

	@Test
	public void test() {
		List<SensorInfoModel> list = new ArrayList<SensorInfoModel>();
		SensorInfoModel model = new SensorInfoModel();
		model.setData_max(0.3);
		list.add(model);
		Gson gson = new Gson();
		String json = gson.toJson(list);
		System.out.println(json);
	}
}
