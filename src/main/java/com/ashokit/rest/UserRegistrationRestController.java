package com.ashokit.rest;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ashokit.entity.User;
import com.ashokit.services.UserService;
@RestController
public class UserRegistrationRestController {
	
	@Autowired
	private UserService userService;
	/*
	 * @GetMapping("/getUser") public ResponseEntity<User> getUser(){ User user=new
	 * User(101, "vishal", "sonule", "vishalsonule2@gmail.com", "123", 9011349907l,
	 * new Date(), "male", 1, 1, 1, "Locked"); return new
	 * ResponseEntity<User>(user,HttpStatus.OK); }
	 */
	
	@GetMapping("/getCountries")
	public ResponseEntity<Map<Integer, String>> handleCountrySelectBox() {
		return new ResponseEntity<Map<Integer, String>>(userService.getCountries(), HttpStatus.OK);
	}
	
	@GetMapping("/getStates")
	public ResponseEntity<Map<Integer, String>> handleStateSelectBox(@RequestParam("countryId") Integer countryId) {
		return new ResponseEntity<Map<Integer, String>>(userService.getSatesByCountry(countryId), HttpStatus.OK);
	}

	@GetMapping("/getCites")
	public ResponseEntity<Map<Integer, String>> handleCitySelectBox(@RequestParam("stateId") Integer stateId) {
		return new ResponseEntity<Map<Integer, String>>(userService.getCitesBySate(stateId), HttpStatus.OK);
	}
	
	@GetMapping("/validMail")
	public ResponseEntity<String> checkMailValidation(@RequestParam("email") String email) {
		return new ResponseEntity<String>(userService.isEmailUnique(email) ? "" : "Mail is Allready Register",
				HttpStatus.OK);
	}
	
	@PostMapping("/registration")
	public ResponseEntity<String> handleRegistrationButton(@RequestBody User user) {
		return new ResponseEntity<String>(
				userService.registerUser(user) ? "Registration successfull" : "registration failed",
				HttpStatus.CREATED);
	}
	
	@PostMapping("/unlockAccount")
	public ResponseEntity<String> handleUnLockAccountButton(@RequestParam("email") String email,
			@RequestParam("tempPass") String tempPassword, @RequestParam("newPassword") String newPassword) {
		return new ResponseEntity<String>(userService.unlockAccount(email, tempPassword, newPassword), HttpStatus.OK);
	}

	@PostMapping("/forgetPassword")
	public ResponseEntity<String> handleForgetPasswordButton(@RequestParam("email") String email) {
		return new ResponseEntity<String>(userService.recoverPassword(email) ? "Password is recoverd,please check mail"
				: "Password is not recoverd,Please try again", HttpStatus.OK);
	}
}
