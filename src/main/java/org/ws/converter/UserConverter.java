package org.ws.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ws.dto.RegisterUserDTO;
import org.ws.entity.UserModel;
import org.ws.kit.Encrypt;
import org.ws.kit.prikit.converter.AbstractBeanConverter;

@Service
public class UserConverter extends AbstractBeanConverter<UserModel,RegisterUserDTO>{

	@Autowired
	private Encrypt encrypt;
	
	@Override
	public UserModel toBean(RegisterUserDTO v) {
		UserModel model = new UserModel();
		model.setCo_email(v.getEmail());
		model.setCo_password(encrypt.SHA512(v.getUserName()+"|^|"+v.getPassword()));
		model.setCo_username(v.getUserName());
		model.setCo_phonenum(v.getPhoneNum());
		return model;
	}

	@Override
	public RegisterUserDTO fromBean(UserModel t) {
		RegisterUserDTO registerUser = new RegisterUserDTO();
		registerUser.setEmail(t.getCo_email());
		registerUser.setPassword(t.getCo_password());
		registerUser.setUserName(t.getCo_username());
		registerUser.setPhoneNum(t.getCo_phonenum());
		return registerUser;
	}

}
