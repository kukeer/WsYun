package org.ws.entity;

import java.sql.Timestamp;

import org.apache.commons.lang.StringUtils;

public class UserModel {
	
	private Long co_id;
	
	private String co_username;
	
	private String co_password;
	
	private String co_email;
	
	private Timestamp co_createtime;
	
	private Integer co_phonenum;

	public Long getCo_id() {
		return co_id;
	}

	public void setCo_id(Long co_id) {
		this.co_id = co_id;
	}

	public String getCo_username() {
		return co_username;
	}

	public void setCo_username(String co_username) {
		this.co_username = co_username;
	}

	public String getCo_password() {
		return co_password;
	}

	public void setCo_password(String co_password) {
		this.co_password = co_password;
	}

	public String getCo_email() {
		return co_email;
	}

	public void setCo_email(String co_email) {
		this.co_email = co_email;
	}

	public Timestamp getCo_createtime() {
		return co_createtime;
	}

	public void setCo_createtime(Timestamp co_createtime) {
		this.co_createtime = co_createtime;
	}

	public Integer getCo_phonenum() {
		return co_phonenum;
	}

	public void setCo_phonenum(Integer co_phonenum) {
		this.co_phonenum = co_phonenum;
	}

	public boolean isNull(){
		if(StringUtils.isBlank(this.co_username)
				||StringUtils.isBlank(this.co_password)
				||StringUtils.isBlank(this.co_email)){
			return false;
		}
		return true;
	}
	
	
}
