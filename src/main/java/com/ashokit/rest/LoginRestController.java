package com.ashokit.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ashokit.model.LoginForm;
import com.ashokit.services.UserService;

@RestController
public class LoginRestController {
	
	@Autowired
	private UserService service;
	
	@PostMapping("/login")
	public ResponseEntity<String>handleLoginButton(@RequestBody LoginForm loginForm) {
		return new ResponseEntity<String>(service.checkUserCredentials(loginForm),HttpStatus.OK);
		
	}

}
