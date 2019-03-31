package org.ws.domain;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.ws.entity.SensorInfoModel;
import org.ws.entity.SensorWarningInfoModel;
@Mapper
public interface SensorDao {
	
	@Insert("insert into cocco_sensor(sensorid,sensor_name,sensor_unit,user_id,data_max,data_min) "
			+ "values(#{sensorid},#{sensor_name},#{sensor_unit},#{user_id},#{data_max},#{data_min})")
	public void saveSensorInfo(SensorInfoModel model);
	
	@Select("select *  from cocco_sensor where sensor_name=#{param1} and user_id=#{param2} limit 1")
	public SensorInfoModel findSensorBySensorNameAndUserId(String sensorName,Long userid);

	@Select("select sensorid  from cocco_sensor where sensor_name=#{param1} and userid=#{param2} limit 1")
	public Long findSensorIdBySensorNameAndUserId(String sensorName,Long userid);
	
	@Update("update cocco_sensor set datamin=#{param3} where user_id=#{param1} and sensor_name=#{param2}")
	public void updateSensorWarnRangeMin(Long userid,String sensorname,Double min);
	
	@Update("update cocco_sensor set datamax=#{parame} where user_id=#{param1} and sensor_name=#{param2}")
	public void updateSensorWarnRangeMax(Long userid,String sensorname,Double max);
	
	@Update("update cocco_sensor set data_max=#{param4},data_min=#{param3} where user_id=#{param1} and sensor_name=#{param2}")
	public void updateSensorWarnRange(Long userid,String sensorname,Double min,Double max);
	
	@Select("select *  from cocco_sensor where user_id=#{param1}")
	public List<SensorInfoModel> findSensorListBySensorNameAndUserId(Long userid);

	@Insert("")
	public void insertIntoSensorWarnInfo(SensorWarningInfoModel model);

}
