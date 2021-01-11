package com.ashokit.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ashokit.constant.AppConstant;
import com.ashokit.entity.City;
import com.ashokit.entity.Country;
import com.ashokit.entity.State;
import com.ashokit.entity.User;
import com.ashokit.model.LoginForm;
import com.ashokit.model.UnlockAccount;
import com.ashokit.properties.AppPropeties;
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

	@Autowired
	private EmailService emailService;

	@Autowired
	private AppPropeties appProperties;

	private Map<Integer, String> map;

	@Override
	public Boolean registerUser(User user) {

		user.setPassword(generateTemporaryPassword());

		user.setStatus("Locked");

		User userObj = userRepository.save(user);

		String body = emailService.createMailBody(userObj);

		String subject = "Unlock your Account | IES";

		Boolean sendMail = emailService.sendMail(subject, body, user.getEmail());

		return userObj.getUserId() != null && sendMail ;
	}

	@Override
	public String checkUserCredentials(LoginForm loginForm) {

		Map<String, String> properties = appProperties.getProperties();

		User user = userRepository.findByEmailAndPassword(loginForm.getEmail(), loginForm.getPassword());

		return user != null
				? (user.getStatus().equalsIgnoreCase(AppConstant.UNLOCKED) ? properties.get(AppConstant.LOGIN_SUCCESS)
						: properties.get(AppConstant.ACCOUNT_LOCK))
				: properties.get(AppConstant.LOGIN_FAIL);
	}

	@Override
	public String generateTemporaryPassword() {
		StringBuilder tempassword = new StringBuilder();
		try {
		final String alphaNumbericValues = AppConstant.ALPHA_NUMERIC_VALUE;
		for (int i = 0; i < 5; i++) {

			tempassword.append(alphaNumbericValues.charAt(new Random().nextInt(alphaNumbericValues.length() - 1)));
		}
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return tempassword.toString();
	}

	@Override
	public String unlockAccount(UnlockAccount acc) {
		Map<String, String> properties = appProperties.getProperties();
		User user = userRepository.findByEmailAndPassword(acc.getEmail(), acc.getTempPwd());
		if (user != null) {
			if (user.getStatus().equalsIgnoreCase(AppConstant.UNLOCKED)) {
				return properties.get(AppConstant.ACCOUNT_ALL_READY_UNLOCK);
			} else {
				if (acc.getConfirmPwd().equals(acc.getNewPwd())) {
					user.setPassword(acc.getNewPwd());
					user.setStatus(AppConstant.UNLOCKED);
					User savedUser = userRepository.save(user);
					if(savedUser!=null) {
					return properties.get(AppConstant.ACCOUNT_UNCLOCK_SUCCESS);
					}
					else {
						return properties.get(AppConstant.ACCOUNT_UNCLOCK_FAIL);
					}
				}
				return properties.get(AppConstant.PASSWORD_MISS_MATCH);
			}
		} else {
			return properties.get(AppConstant.LOGIN_FAIL);
		}
	}

	@Override
	public String recoverPassword(String email) {
		Map<String, String> properties = appProperties.getProperties();
		String subject = "Password Recovered | IES";
		User user = userRepository.findByEmail(email);
		String body = "Your IES password is :: " + user.getPassword();
		Boolean isMailSend = emailService.sendMail(subject, body, email);
		return user != null? isMailSend ? properties.get(AppConstant.RECOVER_PASS_SUCCESS): properties.get(AppConstant.RECOVER_PASS_FAIL): properties.get(AppConstant.INVALID_MAIL);
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
		return userRepository.findByEmail(email) == null;
	}

}
