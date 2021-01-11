package com.ashokit.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ashokit.services.UserService;

@RestController
public class ForgetPasswordRestController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/forgetPassword")
	public ResponseEntity<String> handleForgetPasswordButton(@RequestParam("email") String email) {
		return new ResponseEntity<String>(userService.recoverPassword(email) , HttpStatus.OK);
	}

}
