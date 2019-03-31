package org.ws.dto;

/**
 * @author Administrator
 *
 */
public class SensorDataDTO {
	
	 @Override
	public String toString() {
		return "SensorDataDTO [name=" + name + ", data=" + data + ", time=" + time + "]";
	}
	private String name;
     private Double data;
     private Long time;
     
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getData() {
		return data;
	}
	public void setData(Double data) {
		this.data = data;
	}
	public Long getTime() {
		return time;
	}
	public void setTime(Long time) {
		this.time = time;
	}
     
}
