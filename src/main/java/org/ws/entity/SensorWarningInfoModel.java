package org.ws.entity;

import java.sql.Timestamp;

public class SensorWarningInfoModel {
	
	private Long id;
	
	private Long sensorid;
	
	private boolean warning_info;
	
	private Timestamp warning_start_time;
	
	private Timestamp warning_end_time;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSensorid() {
		return sensorid;
	}

	public void setSensorid(Long sensorid) {
		this.sensorid = sensorid;
	}

	public boolean isWarning_info() {
		return warning_info;
	}

	public void setWarning_info(boolean warning_info) {
		this.warning_info = warning_info;
	}

	public Timestamp getWarning_start_time() {
		return warning_start_time;
	}

	public void setWarning_start_time(Timestamp warning_start_time) {
		this.warning_start_time = warning_start_time;
	}

	public Timestamp getWarning_end_time() {
		return warning_end_time;
	}

	public void setWarning_end_time(Timestamp warning_end_time) {
		this.warning_end_time = warning_end_time;
	}
	
	
	
}
