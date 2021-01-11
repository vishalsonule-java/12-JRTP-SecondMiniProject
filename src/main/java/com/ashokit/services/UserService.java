package com.ashokit.services;

import java.util.Map;

import com.ashokit.entity.User;
import com.ashokit.model.LoginForm;
import com.ashokit.model.UnlockAccount;

public interface UserService {
	
	public Boolean registerUser(User user);
	
	public String checkUserCredentials(LoginForm loginForm);
	
	public String generateTemporaryPassword();
	
	public String unlockAccount(UnlockAccount acc);
	
	public String recoverPassword(String email);
	
	public Map<Integer,String> getCountries();
	
	public Map<Integer,String> getSatesByCountry(Integer country);
	
	public Map<Integer,String> getCitesBySate(Integer state);
	
	public Boolean isEmailUnique(String email);

}
