package com.ashokit.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.ashokit.entity.User;
import com.ashokit.properties.AppPropeties;
import com.ashokit.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(value=UserRegistrationRestController.class)
public class UserRegistrationRestControllerTest {
	
	@MockBean
	private UserService userService;
	
	@MockBean
	private AppPropeties appProperties;
	
	@Autowired
	private MockMvc mockMvc;

	@Test
	public void test_handleCountrySelectBox() throws Exception {
		Map<Integer,String> countries=new HashMap<Integer,String>();
		countries.put(1,"India");
		countries.put(2,"Usa");
		when(userService.getCountries()).thenReturn(countries);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/getCountries");
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
	}
	
	@Test
	public void test_handleStateSelectBox() throws Exception {
		Map<Integer,String> states=new HashMap<Integer,String>();
		states.put(1,"Maharashtra");
		states.put(2,"AP");
		when(userService.getSatesByCountry(1)).thenReturn(states);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/getStates").param("countryId", "1");
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		int status=mvcResult.getResponse().getStatus();
		assertEquals(200, status);
	}
	
	@Test
	public void test_handleCitySelectBox() throws Exception {
		Map<Integer,String> cities=new HashMap<Integer,String>();
		cities.put(1,"Nanded");
		cities.put(2,"Latur");
		when(userService.getCitesBySate(1)).thenReturn(cities);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/getCites").param("stateId", "1");
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		int status=mvcResult.getResponse().getStatus();
		assertEquals(200, status);
	}
	
	@Test
	public void test_1_checkMailValidation() throws Exception {
		when(userService.isEmailUnique("vishalsonule2@gmail.com")).thenReturn(true);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/validMail").param("email", "vishalsonule2@gmail.com");
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		
	}
	@Test
	public void test_2_checkMailValidation() throws Exception {
		when(userService.isEmailUnique("vishalsonule2@gmail.com")).thenReturn(false);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/validMail").param("email", "vishalsonule2@gmail.com");
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(400, status);
		
	}
	
	@Test
	public void test_1_handleRegistrationButton() throws Exception {
		User user= new User();

		when(userService.registerUser(user)).thenReturn(true);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/registration").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(user));
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(201, status);
	}
	
	@Test
	public void test_2_handleRegistrationButton() throws Exception {
		User user= new User();

		when(userService.registerUser(user)).thenReturn(false);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/registration").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(user));
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(400, status);
	}
}
