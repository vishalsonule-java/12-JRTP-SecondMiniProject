package com.ashokit.services;

import java.util.List;
import java.util.Map;

import com.ashokit.entity.User;

public interface UserService {
	
	public Boolean registerUser(User user);
	
	public String checkUserCredentials(String email,String password);
	
	public String generateTemporaryPassword();
	
	public String unlockAccount(String email,String tempPassword,String password);
	
	public Boolean recoverPassword(String email);
	
	public Map<Integer,String> getCountries();
	
	public Map<Integer,String> getSatesByCountry(Integer country);
	
	public Map<Integer,String> getCitesBySate(Integer state);
	
	public Boolean isEmailUnique(String email);

}
