package org.ws.converter;

import org.springframework.stereotype.Service;
import org.ws.dto.SensorInfoDTO;
import org.ws.entity.SensorInfoModel;
import org.ws.kit.prikit.converter.AbstractBeanConverter;

@Service
public class InitSensorConverter extends AbstractBeanConverter<SensorInfoModel,SensorInfoDTO> {
	
	/**
	 * 仅将dto的名称 单位 最大值(阈值) 最小值(阈值)
	 * 转化后还需要 添加 userid 以及 主键id
	 */
	@Override
	public SensorInfoModel toBean(SensorInfoDTO v) {
		String sensorname = v.getSensorname();
		String sensortype = v.getSensortype();
		String unit = v.getUnit();
		Double min = v.getMin();
		Double max = v.getMax();
//		Long userid = v.getUserid();
		SensorInfoModel sensorInfoModel = new SensorInfoModel();
		sensorInfoModel.setSensor_name(sensorname);
		sensorInfoModel.setSensor_unit(unit);
		sensorInfoModel.setData_max(max);
		sensorInfoModel.setData_min(min);
//		sensorInfoModel.setUser_id(userid);
		return sensorInfoModel;
	}
	/**
	 * 仅转化感应器单位以及及名称 极大值 极小值
	 */
	@Override
	public SensorInfoDTO fromBean(SensorInfoModel t) {
		String sensor_name = t.getSensor_name();
		String sensor_unit = t.getSensor_unit();
		Long sensorid = t.getSensorid();
		Long user_id = t.getUser_id();
		Double data_max = t.getData_max();
		Double data_min = t.getData_min();
		SensorInfoDTO dto = new SensorInfoDTO();
		dto.setSensorname(sensor_name);
		dto.setUnit(sensor_unit);
		dto.setMax(data_max);
		dto.setMin(data_min);
//		dto.setUserid(user_id);
		return dto;
	}

}
