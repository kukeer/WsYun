package org.ws.kit.prikit.aspect;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.ws.config.ResponseMsgCode;
import org.ws.dto.ResponseMessageDTO;
import org.ws.kit.Encrypt;
import org.ws.kit.JWT;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Aspect
@Component
public class ChannelAspect {
	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
	
	@Autowired
	private Encrypt encrypt;
	
	 @Pointcut("execution(public * org.wsTest.controller.channel.*.*(..))")  
	    public void webRequest(){}
	 @Around("webRequest()")
	 public Object requestAround(ProceedingJoinPoint pjp) throws Throwable{
//		 String requestBody = GSON.toJson(pjp.getArgs()[0]);
		 ServletRequestAttributes attributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
	     HttpServletRequest request = attributes.getRequest();
	     Cookie[] cookies = request.getCookies();
		 String token = cookies[0].getValue();
		 boolean b = JWT.checkToken(token);
		 
		 if(!b){
			 ResponseMessageDTO message = new ResponseMessageDTO();
			 message.setResponseCode(ResponseMsgCode.TOKEN_UNUSE);
			 message.setResponseMsg(ResponseMsgCode.findMessageByCode(message.getResponseCode()));
			 return message;
		 }
		 Object proceed = pjp.proceed();
		 return proceed;
	 }

}
