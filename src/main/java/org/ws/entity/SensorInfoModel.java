package org.ws.entity;

//@Setter
//@Getter
//@Builder
//@AllArgsConstructor
//@NoArgsConstructor
public class SensorInfoModel {
	
	private Long sensorid;
	private String sensor_name;
	private String sensor_unit;
	private Long user_id;
	private Double data_max;
	private Double data_min;
	
	public Long getSensorid() {
		return sensorid;
	}

	public void setSensorid(Long sensorid) {
		this.sensorid = sensorid;
	}



	public String getSensor_name() {
		return sensor_name;
	}



	public void setSensor_name(String sensor_name) {
		this.sensor_name = sensor_name;
	}



	public String getSensor_unit() {
		return sensor_unit;
	}



	public void setSensor_unit(String sensor_unit) {
		this.sensor_unit = sensor_unit;
	}



	public Long getUser_id() {
		return user_id;
	}



	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}



	public Double getData_max() {
		return data_max;
	}



	public void setData_max(Double data_max) {
		this.data_max = data_max;
	}



	public Double getData_min() {
		return data_min;
	}



	public void setData_min(Double data_min) {
		this.data_min = data_min;
	}



	
}
