package com.daniel.shoppingPlatform.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import com.daniel.shoppingPlatform.dao.UserDao;
import com.daniel.shoppingPlatform.model.User;

import dto.UserLoginRequest;
import dto.UserRegisterRequest;

@Component
public class UserServiceImpl implements UserService {
	
	private final static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserDao userDao;

	@Override
	public Integer register(UserRegisterRequest userRegisterRequest) {
		
		User user = userDao.getUserByEmail(userRegisterRequest.getEmail());
		
		if(user != null) {
			//大括號內的值會帶入後面逗號的參數值, 可以有很多大括號, 有幾個括號後面就要配幾個逗號( 參數值)
			log.warn("email {} 已註冊過", userRegisterRequest.getEmail());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		
		return userDao.createUser(userRegisterRequest);
	}

	@Override
	public User getUserById(Integer userId) {
		
		return userDao.getUserById(userId);
	}

	@Override
	public User login(UserLoginRequest userLoginRequest) {
		
	User user= userDao.getUserByEmail(userLoginRequest.getEmail());
	
	if(user == null) {
		log.warn("該email {} 尚未註冊", userLoginRequest.getEmail());
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
	}
	if(user.getPassword().equals(userLoginRequest.getPassword())) {
		return user;
	}else {
		log.warn("email {} 密碼有誤", userLoginRequest.getPassword());
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
	}
		
	}
	
}
