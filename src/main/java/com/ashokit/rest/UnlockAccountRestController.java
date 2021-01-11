package com.ashokit.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ashokit.model.UnlockAccount;
import com.ashokit.services.UserService;

@RestController
public class UnlockAccountRestController {
	
	@Autowired
	private UserService userService;

	@PostMapping("/unlockAccount")
	public ResponseEntity<String> handleUnLockAccountButton(@RequestBody UnlockAccount acc) {
		return new ResponseEntity<String>(userService.unlockAccount(acc), HttpStatus.OK);
	}
}
