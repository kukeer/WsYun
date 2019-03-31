package org.ws.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ws.config.MongoCollectionMap;
import org.ws.converter.UserConverter;
import org.ws.domain.UserDao;
import org.ws.dto.RegisterUserDTO;
import org.ws.entity.UserModel;
import org.ws.kit.SnowFlake;
import org.ws.service.UserService;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;


@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserConverter userConverter;
	
	@Autowired
	private SnowFlake snowFlake;
	
	@Autowired
	private UserDao dao;
	
	@Autowired
	private MongoCollectionMap dbMap;
	
	@Override
	public boolean userIsExits(String userName, String password) {
		UserModel userModel = new UserModel();
		userModel.setCo_username(userName);
		userModel.setCo_password(password);
		UserModel model = dao.findUserByNamePassword(userModel);
		if(model==null) {
			return false;
		}
		return true;
	}

	@Override
	public Long saveUser(RegisterUserDTO user) {
		UserModel bean = userConverter.toBean(user);
		bean.setCo_id(snowFlake.nextId());
		dao.saveUser(bean);
		return bean.getCo_id();
	}

	@Override
	public UserModel findUserByUserNamePassword(String userName, String password) {
		UserModel userModel = new UserModel();
		userModel.setCo_username(userName);
		userModel.setCo_password(password);
		UserModel model = dao.findUserByNamePassword(userModel);
		return model;
	}

	@Override
	public void initUserInfo(Long userid) {
		MongoClient mongoClient = new MongoClient();
		DB db = mongoClient.getDB("sensor");
		DBCollection collection = db.getCollection(""+userid);
		dbMap.putDBInfo(userid, collection);
//		List<DBObject> indexInfo = collection.getIndexInfo();
//		for(DBObject dbObj:indexInfo){
//			dbObj.
//		}
	}
}
