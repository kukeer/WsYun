package org.ws.kit;

import java.security.Key;
import java.sql.Timestamp;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.ws.entity.UserModel;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

public class JWT {
	
	private static String JWT_KEY = "snah29qjmLklasmd.aasknq";
	
	private static Key key = null;
	
	private static int EXP_DAYS= 30;
	
	static {
		SecretKeyFactory keyFactory;
		try {
			keyFactory = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
			PBEKeySpec pbeKeySpec = new PBEKeySpec(JWT_KEY.toCharArray());
			key = keyFactory.generateSecret(pbeKeySpec);
		}catch (Exception e) {
			//发生异常时在这里处理
		}
		
	}
	
	public static String create(UserModel user) {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		user.setCo_createtime(timestamp);
		String json = JSON.toJSONString(user);
		String compact = Jwts.builder().setSubject(user.getCo_id().toString())
				.setClaims(JSON.parseObject(json))//claim set是一个json数据，是表明用户身份的数据
				.setExpiration(new DateTime().plusDays(EXP_DAYS).toDate())
				.signWith(io.jsonwebtoken.SignatureAlgorithm.HS512, key).compact();
		return compact;
	}
	
	public static UserModel parse(String compactJws) {
		if(compactJws == null) {}
		Jws<Claims> jws = Jwts.parser().setSigningKey(key).parseClaimsJws(compactJws);
		Claims body = jws.getBody();
		JSONObject object = new JSONObject(body);
		return JSON.toJavaObject(object, UserModel.class);
	}
	
	public static boolean checkToken(String compactJws) {
		try {
			Jwts.parser().setSigningKey(key).parseClaimsJws(compactJws);
			return true;
		}catch(Exception e) {
			return false;
		}
	}
	
	public static String getToken(HttpServletRequest request) {
		String jwt = request.getHeader("Authorization");
		if(jwt == null) {
			return null;
		}
		jwt.replace("bearer", "").trim();
		return jwt;
	}
	
}
