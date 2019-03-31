package org.ws.controller.channel;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.ws.config.ResponseMsgCode;
import org.ws.dto.ResponseMessageDTO;
import org.ws.dto.SensorInfoDTO;
import org.ws.entity.UserModel;
import org.ws.kit.JWT;
import org.ws.service.SensorInfoService;
import org.ws.service.UserService;

import com.google.gson.Gson;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;



@RestController
public class SensorController {
	
	Logger log = LoggerFactory.getLogger(getClass());
	
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private SensorInfoService sensorInfoservice;
	
//	@Autowired
//	private UserConverter converter;
	
	
	@PostMapping("/initSensorInfo")
	public String saveUserInfo(@RequestBody JSONObject obj,HttpServletRequest request ){
		log.info("进入到了initInfo信息内"+obj);
		ResponseMessageDTO message = new ResponseMessageDTO();
		String value = request.getCookies()[0].getValue();
		
		UserModel parse = JWT.parse(value);
		Long co_id = parse.getCo_id();
		UserModel model = userService.findUserByUserNamePassword(parse.getCo_username(), parse.getCo_password());
		
		if(model==null){
			message.setResponseCode(ResponseMsgCode.USERNAME_NOT_FIND);
			message.setResponseMsg(ResponseMsgCode.findMessageByCode(ResponseMsgCode.USERNAME_NOT_FIND));
			return message.getResponseMsg(message);
		}
		
		sensorInfoservice.putInfo2Data(co_id, obj);
		message.setResponseCode(ResponseMsgCode.SUCCESS);
		message.setResponseMsg(ResponseMsgCode.findMessageByCode(ResponseMsgCode.SUCCESS));
		return message.getResponseMsg(message);
	}
	
	@PostMapping("/sendSensorInfo")
	public String sendSensorInfo(@RequestBody JSONObject obj,HttpServletRequest request ){
		
		ResponseMessageDTO message = new ResponseMessageDTO();
		String value = request.getCookies()[0].getValue();
		
		UserModel parse = JWT.parse(value);
		Long co_id = parse.getCo_id();
		UserModel model = userService.findUserByUserNamePassword(parse.getCo_username(), parse.getCo_password());
		
		if(model==null){
			message.setResponseCode(ResponseMsgCode.USERNAME_NOT_FIND);
			message.setResponseMsg(ResponseMsgCode.findMessageByCode(ResponseMsgCode.USERNAME_NOT_FIND));
			return message.getResponseMsg(message);
		}
		sensorInfoservice.putsensor2Mongo(co_id, obj,model.getCo_username());
		message.setResponseCode(ResponseMsgCode.SUCCESS);
		message.setResponseMsg(ResponseMsgCode.findMessageByCode(ResponseMsgCode.SUCCESS));
		return message.getResponseMsg(message);
		
	}
	
	@PostMapping("/changeInsensorInfo")
	public String changeInsensorInfo(@RequestBody JSONObject obj,HttpServletRequest request ){
		log.info("msg changeInsensorInfo"+obj);
		ResponseMessageDTO message = new ResponseMessageDTO();
		String value = request.getCookies()[0].getValue();
		
		UserModel parse = JWT.parse(value);
		Long co_id = parse.getCo_id();
		UserModel model = userService.findUserByUserNamePassword(parse.getCo_username(), parse.getCo_password());
		
		if(model==null){
			message.setResponseCode(ResponseMsgCode.USERNAME_NOT_FIND);
			message.setResponseMsg(ResponseMsgCode.findMessageByCode(ResponseMsgCode.USERNAME_NOT_FIND));
			return message.getResponseMsg(message);
		}
		//
		sensorInfoservice.changeSensorRange(co_id, obj);
		
		message.setResponseCode(ResponseMsgCode.SUCCESS);
		message.setResponseMsg(ResponseMsgCode.findMessageByCode(ResponseMsgCode.SUCCESS));
		log.info(message.getResponseMsg(message));
		return message.getResponseMsg(message);
	}
	
	@GetMapping("/getSensorList")
	public String getSensorList(HttpServletRequest request ){
		log.info("msg getSensorList");
		ResponseMessageDTO message = new ResponseMessageDTO();
		String value = request.getCookies()[0].getValue();
		
		UserModel parse = JWT.parse(value);
		Long co_id = parse.getCo_id();
		UserModel model = userService.findUserByUserNamePassword(parse.getCo_username(), parse.getCo_password());
		
		if(model==null){
			message.setResponseCode(ResponseMsgCode.USERNAME_NOT_FIND);
			message.setResponseMsg(ResponseMsgCode.findMessageByCode(ResponseMsgCode.USERNAME_NOT_FIND));
			return message.getResponseMsg(message);
		}
		//
		List<SensorInfoDTO> list = sensorInfoservice.getSensorList(co_id);
		message.setResponseCode(ResponseMsgCode.SUCCESS);
		message.setResponseMsg(ResponseMsgCode.findMessageByCode(ResponseMsgCode.SUCCESS));
		Gson gson = new Gson();
		String json = gson.toJson(message);
		String jsonArray = gson.toJson(list);
//		message.setData(gson.toJson(list));
		JSONObject obij = JSONObject.fromObject(json);
		JSONArray object = JSONArray.fromObject(jsonArray);
		
		obij.put("data", object);
		log.info("msg getSensorList returns "+obij);
		return obij.toString();
	}
	
}
