package org.ws.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.ws.config.ResponseMsgCode;
import org.ws.converter.UserConverter;
import org.ws.dto.LoginUserDTO;
import org.ws.dto.RegisterUserDTO;
import org.ws.dto.ResponseMessageDTO;
import org.ws.entity.UserModel;
import org.ws.kit.Encrypt;
import org.ws.kit.JWT;
import org.ws.service.UserService;

import net.sf.json.JSONObject;

@RestController
public class h5connector {
	
	Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private UserConverter converter;
	
	@Autowired
	private Encrypt encrypt;
	
//	@Autowired
//	private LoginUserConverter loginConverter;
	
	@Autowired
	private UserService userService;

	@PostMapping("/login")
	public String toLogin(@RequestBody LoginUserDTO user) {
		log.info("进入到了 login内");
		ResponseMessageDTO message = new ResponseMessageDTO();
		
		if (StringUtils.isEmpty(user) || StringUtils.isEmpty(user.getIMEI()) || StringUtils.isEmpty(user.getPassword())
				|| StringUtils.isEmpty(user.getUserName())) {
			message.setResponseCode(ResponseMsgCode.USERINFO_MISSING);
		}
		UserModel model = userService.findUserByUserNamePassword(user.getUserName(), encrypt.SHA512(user.getUserName()+"|^|"+user.getPassword()));
		
		if (model==null) {
			message.setResponseCode(ResponseMsgCode.USERNAME_NOT_FIND);
		}else{
			message.setToken(JWT.create(model));
			message.setResponseCode(ResponseMsgCode.SUCCESS);
		}
		
		message.setResponseMsg(ResponseMsgCode.findMessageByCode(message.getResponseCode()));
		return message.getResponseMsg(message);
	}

	@PostMapping("/register")
	public String toRegister(@RequestBody String str) {
		log.info("进入到了 register内");
		JSONObject obj = JSONObject.fromObject(str);
		RegisterUserDTO user = getJSON2User(obj);
		ResponseMessageDTO message = new ResponseMessageDTO();
		// 判断传入参数是否缺失
		if (StringUtils.isEmpty(user) || StringUtils.isEmpty(user.getEmail()) || StringUtils.isEmpty(user.getIMEI())
				|| StringUtils.isEmpty(user.getPassword()) || StringUtils.isEmpty(user.getUserName())) {
			message.setResponseCode(ResponseMsgCode.USERINFO_MISSING);
		}

		boolean userIsExits = userService.userIsExits(user.getUserName(), user.getPassword());
		if (userIsExits) {
			message.setResponseCode(ResponseMsgCode.USERNAME_EXITS);
		} else {
			try {
				Long saveUser = userService.saveUser(user);
				UserModel bean = converter.toBean(user);
				bean.setCo_id(saveUser);
				message.setToken(JWT.create(bean));
				message.setResponseCode(ResponseMsgCode.SUCCESS);
			} catch (Exception e) {
				 e.printStackTrace();
				message.setResponseCode(ResponseMsgCode.EXCEPTION_ERROR);
			}
		}

		message.setResponseMsg(ResponseMsgCode.findMessageByCode(message.getResponseCode()));
		return message.getResponseMsg(message);
	}
	private RegisterUserDTO getJSON2User(JSONObject obj){
		RegisterUserDTO user = new RegisterUserDTO();
		user.setEmail(obj.getString("email"));
		user.setIMEI(obj.getString("IMEI"));
		user.setPassword(obj.getString("password"));
		user.setUserName(obj.getString("userName"));
		return user;
	}


}
