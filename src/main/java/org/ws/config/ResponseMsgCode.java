package org.ws.config;

import java.util.HashMap;
import java.util.Map;

public class ResponseMsgCode {
	
	//注册部分返回码(失败)
	public final static Long USERNAME_EXITS =990L;
	public final static Long EMAIL_EXITS =980L;
	public final static Long EXCEPTION_ERROR=970L;
	
	//登录部分返回码(失败)
	public final static Long ERROR_PASSWORD =790L;
	public final static Long USERNAME_NOT_FIND =780L;
	
	public final static Long USERINFO_MISSING=690L;
	public final static Long TOKEN_UNUSE=680L;
	//成功返回
	public final static Long SUCCESS =0L;
	
	public final static Map<Long,String> message = new HashMap<Long,String>();
	
	static{
		message.put(EMAIL_EXITS, "该邮箱已被注册");
		message.put(ERROR_PASSWORD, "用户名或密码错误");
		message.put(SUCCESS, "成功");
		message.put(USERNAME_EXITS, "用户名或密码错误");
		message.put(USERNAME_NOT_FIND, "该用户名不存在");
		message.put(EXCEPTION_ERROR, "注册时发生了异常");
		message.put(USERINFO_MISSING, "用户信息缺失");
	}
	
	public static String findMessageByCode(Long code){
		return message.get(code);
	}
}
