package com.ashokit.rest;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ashokit.constant.AppConstant;
import com.ashokit.entity.User;
import com.ashokit.properties.AppPropeties;
import com.ashokit.services.UserService;
@RestController
public class UserRegistrationRestController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AppPropeties appProperties;
	
	
			
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
		
		Map<String, String> properties = appProperties.getProperties();
		
		Boolean isValid=userService.isEmailUnique(email);
		
		if(isValid) {
			
			return new ResponseEntity<String>(properties.get(""),HttpStatus.OK);
		}
		else
		return new ResponseEntity<String>(properties.get(AppConstant.INVALID_MAIL),HttpStatus.BAD_REQUEST);
	}
	
	@PostMapping("/registration")
	public ResponseEntity<String> handleRegistrationButton(@RequestBody User user) {
		
		Map<String, String> properties = appProperties.getProperties();
		
		Boolean isRegister=userService.registerUser(user);
		
		if(isRegister) {
			
			return new ResponseEntity<String>(properties.get(AppConstant.USER_REGISTRATION_SUCC),HttpStatus.CREATED);
		}
		else
			
		return new ResponseEntity<String>(properties.get(AppConstant.USER_REGISTRATION_FAIL),HttpStatus.BAD_REQUEST);
	}	
}
