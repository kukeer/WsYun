package org.ws.domain;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.ws.entity.UserModel;

@Mapper
public interface UserDao {
	
	@Insert("insert into cooco_user(co_id,co_username,"
			+ "co_password,co_createtime,co_email)"
			+ " values(#{co_id},#{co_username},#{co_password}"
			+ ",#{co_createtime},#{co_email})")
	public void saveUser(UserModel model);
	
	@Select("Select * from cooco_user where co_username=#{co_username} and co_password=#{co_password} limit 1")
	public UserModel findUserByNamePassword(UserModel user);
	
	
}
