package com.ashokit.service;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

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
import com.ashokit.services.EmailService;
import com.ashokit.services.UserService;

@SpringBootTest
public class UserServiceTest {

	@MockBean
	private CountryRepository countryRepo;

	@MockBean
	private StateRepository stateRepo;

	@MockBean
	private CityRepository cityRepo;

	@MockBean
	private UserRepository userRepo;

	@Autowired
	private UserService userService;

	@MockBean
	private EmailService emailService;

	@MockBean
	private AppPropeties appPropeties;
	
	
	@BeforeEach
	public void beforeAll() {
		Map<String,String> map= new HashMap<String, String>();
		map.put("loginFail", "Invalid User Credentials");
		map.put("accLock", "Your Account is Locked");
		map.put("accUnlockSucc", "Account unlocked, please proceed with login");
		map.put("passwordMisMatch", "Confirm Password is not match,Please Enter both  password same");
		map.put("accAllReadyUnlock", "Account is AllReady Unlcoked, please proceed with login");
		map.put("recoverPassSucc", "Password is recoverd,please check mail");
		when(appPropeties.getProperties()).thenReturn(map);
	}

	@Test
	public void test_getCountries() {
		ArrayList<Country> list = new ArrayList<Country>();
		Country india = new Country();
		india.setCountryId(1);
		india.setCountryName("India");
		list.add(india);
		Map<Integer, String> map = new HashMap<Integer, String>();
		map.put(india.getCountryId(), india.getCountryName());
		when(countryRepo.findAll()).thenReturn(list);
		assertEquals(userService.getCountries(), map);
	}

	@Test
	public void test_getStates() {
		ArrayList<State> list = new ArrayList<State>();
		State s = new State();
		s.setCountryId(1);
		s.setStateId(101);
		s.setStateName("maharashtra");
		list.add(s);
		Map<Integer, String> map = new HashMap<Integer, String>();
		map.put(s.getStateId(), s.getStateName());
		when(stateRepo.findByCountryId(1)).thenReturn(list);
		assertEquals(userService.getSatesByCountry(1), map);
	}

	@Test
	public void test_getCities() {
		ArrayList<City> list = new ArrayList<City>();
		City city = new City();
		city.setCityId(1001);
		city.setCityName("nanded");
		city.setStateId(101);
		list.add(city);
		Map<Integer, String> map = new HashMap<Integer, String>();
		map.put(city.getCityId(), city.getCityName());
		when(cityRepo.findByStateId(101)).thenReturn(list);
		assertEquals(map, userService.getCitesBySate(101));
	}

	@Test
	public void test_isEmailUnique() {
		 when(userRepo.findByEmail("vishalsonule2@gmail.com")).thenReturn(null);
		 assertEquals(userService.isEmailUnique("vishalsonule2@gmail.com"),true);
	}
	
	@Test
	public void test_1_unlockAccount() {
		User user= new User();
		user.setEmail("vishalsonule2@gmail.com");
		user.setPassword("abcde");
		UnlockAccount acc= new UnlockAccount();
		acc.setEmail("vishalsonule2@gmail.com");
		acc.setTempPwd("abcd");
		acc.setNewPwd("vishal@123");
		acc.setNewPwd("vishal@123");
		when(userRepo.findByEmailAndPassword(acc.getEmail(), acc.getTempPwd())).thenReturn(null);
		String result=userService.unlockAccount(acc);
		assertEquals(result, appPropeties.getProperties().get(AppConstant.LOGIN_FAIL));
	}
	
	@Test
	public void test_2_unlockAccount() {
		User user= new User();
		user.setEmail("vishalsonule2@gmail.com");
		user.setPassword("abcd");
		user.setStatus("Locked");
		UnlockAccount acc= new UnlockAccount();
		acc.setEmail("vishalsonule2@gmail.com");
		acc.setTempPwd("abcd");
		acc.setNewPwd("vishal@123");
		acc.setConfirmPwd("vishal@123");
		when(userRepo.findByEmailAndPassword(acc.getEmail(), acc.getTempPwd())).thenReturn(user);
		String result=userService.unlockAccount(acc);
		assertEquals(result, appPropeties.getProperties().get(AppConstant.ACCOUNT_UNCLOCK_SUCCESS));
	}
	
	@Test
	public void test_3_unlockAccount() {
		User user= new User();
		user.setEmail("vishalsonule2@gmail.com");
		user.setPassword("abcd");
		user.setStatus("Locked");
		UnlockAccount acc= new UnlockAccount();
		acc.setEmail("vishalsonule2@gmail.com");
		acc.setTempPwd("abcd");
		acc.setNewPwd("vishal@123");
		acc.setConfirmPwd("vishal@1234");
		when(userRepo.findByEmailAndPassword(acc.getEmail(), acc.getTempPwd())).thenReturn(user);
		String result=userService.unlockAccount(acc);
		assertEquals(result, appPropeties.getProperties().get(AppConstant.PASSWORD_MISS_MATCH));
	}
	
	@Test
	public void test_4_unlockAccount() {
		User user= new User();
		user.setEmail("vishalsonule2@gmail.com");
		user.setPassword("abcd");
		user.setStatus("Unlocked");
		UnlockAccount acc= new UnlockAccount();
		acc.setEmail("vishalsonule2@gmail.com");
		acc.setTempPwd("abcd");
		acc.setNewPwd("vishal@123");
		acc.setConfirmPwd("vishal@123");
		when(userRepo.findByEmailAndPassword(acc.getEmail(), acc.getTempPwd())).thenReturn(user);
		String result=userService.unlockAccount(acc);
		assertEquals(result, appPropeties.getProperties().get(AppConstant.ACCOUNT_ALL_READY_UNLOCK));
	}
	
	@Test
	public void test_1_checkUserCredentials() {
		User user= new User();
		user.setEmail("vishalsonule2@gmail.com");
		user.setPassword("vishal@123");
		user.setStatus("Locked");
		LoginForm loginForm= new LoginForm();
		loginForm.setEmail("vishalsonule2@gmail.com");
		loginForm.setPassword("vishal@123");
		when(userRepo.findByEmailAndPassword(loginForm.getEmail(), loginForm.getPassword())).thenReturn(user);
		String result=userService.checkUserCredentials(loginForm);
		
		assertEquals(result, appPropeties.getProperties().get(AppConstant.ACCOUNT_LOCK));
	}
	
	@Test
	public void test_2_checkUserCredentials() {
		User user= new User();
		user.setEmail("vishalsonule2@gmail.com");
		user.setPassword("vishal@1234");
		user.setStatus("Locked");
		LoginForm loginForm= new LoginForm();
		loginForm.setEmail("vishalsonule2@gmail.com");
		loginForm.setPassword("vishal@123");
		when(userRepo.findByEmailAndPassword(loginForm.getEmail(), loginForm.getPassword())).thenReturn(null);
		String result=userService.checkUserCredentials(loginForm);
		
		assertEquals(result, appPropeties.getProperties().get(AppConstant.LOGIN_FAIL));
	}
	
	@Test
	public void test_3_checkUserCredentials() {
		User user= new User();
		user.setEmail("vishalsonule2@gmail.com");
		user.setPassword("vishal@123");
		user.setStatus("Unlocked");
		LoginForm loginForm= new LoginForm();
		loginForm.setEmail("vishalsonule2@gmail.com");
		loginForm.setPassword("vishal@123");
		when(userRepo.findByEmailAndPassword(loginForm.getEmail(), loginForm.getPassword())).thenReturn(user);
		String result=userService.checkUserCredentials(loginForm);
		
		assertEquals(result, appPropeties.getProperties().get(AppConstant.LOGIN_SUCCESS));
	}
	
	@Test
	public void test_generateTemporaryPassword() {
		assertThatNoException().isThrownBy(()->{
			userService.generateTemporaryPassword();
		});
		
	}
	
	/*
	 * @Test public void test_recoverPassword() { User user= new User();
	 * user.setEmail("vishalsonule2@gmail.com"); user.setPassword("vishal@123");
	 * when(userRepo.findByEmail("vishalsonule2@gmail.com")).thenReturn(user);
	 * when(emailService.sendMail("", "",
	 * "vishalsonule2@gmail.com")).thenReturn(true); String
	 * result=userService.recoverPassword("vishalsonule2@gmail.com");
	 * assertEquals(result,
	 * appPropeties.getProperties().get(AppConstant.RECOVER_PASS_SUCCESS));
	 * 
	 * }
	 */
}
