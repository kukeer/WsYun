package org.ws.dto;

import java.sql.Timestamp;

public class TopicInfoDTO {
	private String topic;
	
	private Long sensorId;
	
	private String sensorname;
	
	private Timestamp createdate;
	
	private Double warnData;
	
	private Double minSecureData;
	
	private Double maxSecureData;

	private String unit;
	
	
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public Long getSensorId() {
		return sensorId;
	}

	public void setSensorId(Long sensorId) {
		this.sensorId = sensorId;
	}

	public String getSensorname() {
		return sensorname;
	}

	public void setSensorname(String sensorname) {
		this.sensorname = sensorname;
	}

	public Timestamp getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Timestamp createdate) {
		this.createdate = createdate;
	}

	public Double getWarnData() {
		return warnData;
	}

	public void setWarnData(Double warnData) {
		this.warnData = warnData;
	}

	public Double getMinSecureData() {
		return minSecureData;
	}

	public void setMinSecureData(Double minSecureData) {
		this.minSecureData = minSecureData;
	}

	public Double getMaxSecureData() {
		return maxSecureData;
	}

	public void setMaxSecureData(Double maxSecureData) {
		this.maxSecureData = maxSecureData;
	}
}
