package org.ws.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ws.dto.LoginUserDTO;
import org.ws.entity.UserModel;
import org.ws.kit.Encrypt;
import org.ws.kit.prikit.converter.AbstractBeanConverter;
@Service
public class LoginUserConverter extends AbstractBeanConverter<UserModel,LoginUserDTO> {

	@Autowired
	private Encrypt encrypt;
	
	@Override
	public UserModel toBean(LoginUserDTO v) {
		UserModel model = new UserModel();
//		model.setEmail(v.getEmail());
		model.setCo_password(encrypt.SHA512(v.getUserName()+"|^|"+v.getPassword()));
		model.setCo_username(v.getUserName());
//		model.setPhoneNum(v.getPhoneNum());
		return model;
	}

	@Override
	public LoginUserDTO fromBean(UserModel t) {
		// TODO Auto-generated method stub
		LoginUserDTO user = new LoginUserDTO();
		user.setUserName(t.getCo_username());
		user.setPassword(t.getCo_password());
		return null;
	}

}
