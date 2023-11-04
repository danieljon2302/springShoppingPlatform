package com.daniel.shoppingPlatform.service;

import com.daniel.shoppingPlatform.model.User;

import dto.UserLoginRequest;
import dto.UserRegisterRequest;

public interface UserService {

	User getUserById(Integer userId);
	
	Integer register(UserRegisterRequest userRegisterRequest);
	
	User login(UserLoginRequest userLoginRequest);
	
}
