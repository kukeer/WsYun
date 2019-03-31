package org.ws.service;

import java.util.List;

import org.ws.dto.SensorInfoDTO;

import net.sf.json.JSONObject;

public interface SensorInfoService {
	/**
	 * 将传感器概要信息传入数据库并对其对应的mongo表做初始化操作
	 * @param userId
	 * @param obj
	 */
	public void putInfo2Data(Long userId,JSONObject obj);


	public void putsensor2Mongo(Long userId,JSONObject obj,String username);

	public void changeSensorRange(Long userId,JSONObject obj);
	
	public List<SensorInfoDTO> getSensorList(Long userid);
}
