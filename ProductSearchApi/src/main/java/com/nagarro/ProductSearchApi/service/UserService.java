package com.nagarro.ProductSearchApi.service;

import com.nagarro.ProductSearchApi.model.User;

public interface UserService {

	String loginUser(String email, String password);

	User getUserByEmail(String email);

	String registerUser(User user);

}
