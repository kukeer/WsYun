package org.ws.service;

import org.ws.dto.RegisterUserDTO;
import org.ws.entity.UserModel;

public interface UserService {

	public boolean userIsExits(String userName,String password);
	
	public Long saveUser(RegisterUserDTO user);
	
	public UserModel findUserByUserNamePassword(String userName,String password);
	
	public void initUserInfo(Long userid);
}
