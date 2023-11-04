package com.daniel.shoppingPlatform.dao;

import com.daniel.shoppingPlatform.model.User;

import dto.UserRegisterRequest;

public interface UserDao {
	
	User getUserById(Integer userId);
	
	User getUserByEmail(String email);

	Integer createUser(UserRegisterRequest userRegisterRequest);
	
}
