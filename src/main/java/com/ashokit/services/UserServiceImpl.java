package com.ashokit.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ashokit.entity.City;
import com.ashokit.entity.Country;
import com.ashokit.entity.State;
import com.ashokit.entity.User;
import com.ashokit.repo.CityRepository;
import com.ashokit.repo.CountryRepository;
import com.ashokit.repo.StateRepository;
import com.ashokit.repo.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private CountryRepository countryRepository;

	@Autowired
	private StateRepository stateRepository;

	@Autowired
	private CityRepository cityRepository;

	@Autowired
	private UserRepository userRepository;

	private Map<Integer, String> map;

	@Override
	public Boolean registerUser(User user) {
		// TODO Sending mail is pending
		user.setPassword(generateTemporaryPassword());
		user.setStatus("Locked");
		User userObj = userRepository.save(user);
		return userObj.getUserId() != null;
	}

	@Override
	public String checkUserCredentials(String email, String password) {

		User user = userRepository.findByEmailAndPassword(email, password);

		return user != null
				? (user.getStatus().equalsIgnoreCase("Unlocked") ? "Login successfully...!" : "Your Account is Locked")
				: "Invalid User Credentials";
	}

	@Override
	public String generateTemporaryPassword() {
		final  String alphaNumbericValues="abcedfghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ123456789";
		StringBuilder tempassword=new StringBuilder();
		for(int i=0;i<5;i++) {
			tempassword.append(alphaNumbericValues.charAt(new Random().nextInt(alphaNumbericValues.length()-1)));
		}
		return tempassword.toString();
	}

	@Override
	public String unlockAccount(String email, String tempPassword, String password) {
		// TODO Sending mail is pending
		String result;
		User user = userRepository.findByEmailAndPassword(email, tempPassword);
		if (user.getStatus().equalsIgnoreCase("Unlocked"))
			result = "Account is All Ready Unlocked";
		else {
			user.setPassword(password);
			user.setStatus("Unlocked");
			User userObj = userRepository.save(user);
			result = userObj != null ? "User is Unloked Successfully.." : "User is not Unloked,Please try again";
		}
		return result;
	}

	@Override
	public Boolean recoverPassword(String email) {
		// TODO Sending mail is pending
		User user = userRepository.findByEmail(email);
		return user.getPassword()!=null;
	}

	@Override
	public Map<Integer, String> getCountries() {
		map = new HashMap<Integer, String>();
		List<Country> countries = countryRepository.findAll();
		countries.forEach(country -> {
			map.put(country.getCountryId(), country.getCountryName());
		});
		return map;
	}

	@Override
	public Map<Integer, String> getSatesByCountry(Integer country) {
		map = new HashMap<Integer, String>();
		List<State> states = stateRepository.findByCountryId(country);
		states.forEach(state -> {
			map.put(state.getStateId(), state.getStateName());
		});
		return map;
	}

	@Override
	public Map<Integer, String> getCitesBySate(Integer state) {
		map = new HashMap<Integer, String>();
		List<City> cites = cityRepository.findByStateId(state);
		cites.forEach(city -> {
			map.put(city.getCityId(), city.getCityName());
		});
		return map;
	}

	@Override
	public Boolean isEmailUnique(String email) {
		return userRepository.findByEmail(email)==null;
	}

}
