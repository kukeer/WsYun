package org.ws.dto;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

public class ResponseMessageDTO {
	private Long responseCode;
	
	private String responseMsg;
	
	private String token;

	private String data;
	


	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public Long getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(Long responseCode) {
		this.responseCode = responseCode;
	}

	public String getResponseMsg() {
		return responseMsg;
	}

	public void setResponseMsg(String responseMsg) {
		this.responseMsg = responseMsg;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	public String getResponseMsg(ResponseMessageDTO msg){
	    Map<String,String> responseMap = new HashMap<String,String>();
	    Field[] declaredFields = this.getClass().getDeclaredFields();
	    for(Field f:declaredFields){
	    	try {
				if(f.get(msg)!=null){
					responseMap.put(f.getName(), f.get(msg)+"");
				}
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
	    }
		JSONObject object = JSONObject.fromObject(responseMap);
		return object.toString();
	}
}
