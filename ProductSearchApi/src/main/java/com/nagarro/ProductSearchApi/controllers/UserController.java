package com.nagarro.ProductSearchApi.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.nagarro.ProductSearchApi.dto.JwtResponse;
import com.nagarro.ProductSearchApi.exception.DuplicateEmailException;
import com.nagarro.ProductSearchApi.exception.InvalidCredentialsException;
import com.nagarro.ProductSearchApi.model.User;
import com.nagarro.ProductSearchApi.service.UserService;
import com.nagarro.ProductSearchApi.dto.loginRequest;

@RestController
@Validated
public class UserController {

	@Autowired
	private UserService userService;

	/*
	POST: Authenticate user
	*/
	@PostMapping("/authenticate")
	public ResponseEntity<?> loginUser(@RequestBody loginRequest loginRequest) {
		try {
			String token = userService.loginUser(loginRequest.getEmail(), loginRequest.getPassword());
			return ResponseEntity.ok(new JwtResponse(token));
		} catch (InvalidCredentialsException e) {
			Map<String, Object> errorResponse = new HashMap<>();
			errorResponse.put("message", e.getMessage());
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
		}
	}

	/*
	POST: Register user
	*/
	@PostMapping("/register")
	public ResponseEntity<?> signUpUser(@Validated @RequestBody User user, BindingResult bindingResult) {
		// Check for all fields are present
		if (bindingResult.hasErrors()) {
			List<String> errors = new ArrayList<>();
			for (FieldError error : bindingResult.getFieldErrors()) {
				errors.add(error.getDefaultMessage());
			}
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
		}

		try {
			 String token = userService.registerUser(user);
			 JwtResponse jwtResponse = new JwtResponse(token);
	          return new ResponseEntity<>(jwtResponse, HttpStatus.CREATED);
		} catch (DuplicateEmailException e) {
			Map<String, Object> errorResponse = new HashMap<>();
			errorResponse.put("message", e.getMessage());
			return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
		}
	}

}
