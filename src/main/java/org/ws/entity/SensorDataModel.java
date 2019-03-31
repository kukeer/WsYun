package org.ws.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 *
 */
public class SensorDataModel {
	
	private Long sensorid;
	private Long basictime;
	private Double data;
	
	public Double getData() {
		return data;
	}
	public void setData(Double data) {
		this.data = data;
	}
	public Long getSensorid() {
		return sensorid;
	}
	public void setSensorid(Long sensorid) {
		this.sensorid = sensorid;
	}
	public Long getBasictime() {
		return basictime;
	}
	public void setBasictime(Long basictime) {
		this.basictime = basictime;
	}
	@Override
	public String toString() {
		return "SensorDataModel [sensorid=" + sensorid + ", basictime=" + basictime + ", data=" + data + "]";
	}
	
}
