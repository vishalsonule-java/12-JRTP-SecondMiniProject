package com.ashokit.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.ashokit.model.LoginForm;
import com.ashokit.properties.AppPropeties;
import com.ashokit.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(value = LoginRestController.class)
public class LoginRestControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private UserService userSerive;
	
	@MockBean
	private AppPropeties appProperties;
	
	@Test
	public void test_1_handleLoginButton() throws Exception {
		LoginForm loginform = new LoginForm();
		when(userSerive.checkUserCredentials(loginform)).thenReturn("login successfull.");
		
		loginform.setEmail("vishalsonule2@gmail.com");
		loginform.setPassword("vishal@123");
		ObjectMapper objMapper = new ObjectMapper();
		String loginformJson = objMapper.writeValueAsString(loginform);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/login")
				.contentType(MediaType.APPLICATION_JSON).content(loginformJson);
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		String body=mvcResult.getResponse().getContentAsString();
		assertEquals("login successfull.", body);

	}
	
	@Test
	public void test_2_handleLoginButton() throws Exception {
		LoginForm loginform = new LoginForm();
		when(userSerive.checkUserCredentials(loginform)).thenReturn("login fail.");
		
		loginform.setEmail("vishalsonule2@gmail.com");
		loginform.setPassword("vishal@123");
		ObjectMapper objMapper = new ObjectMapper();
		String loginformJson = objMapper.writeValueAsString(loginform);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/login")
				.contentType(MediaType.APPLICATION_JSON).content(loginformJson);
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		String body=mvcResult.getResponse().getContentAsString();
		assertEquals("login fail.", body);

	}
	
	@Test
	public void test_3_handleLoginButton() throws Exception {
		LoginForm loginform = new LoginForm();
		when(userSerive.checkUserCredentials(loginform)).thenReturn("Account is Locked");
		
		loginform.setEmail("vishalsonule2@gmail.com");
		loginform.setPassword("vishal@123");
		ObjectMapper objMapper = new ObjectMapper();
		String loginformJson = objMapper.writeValueAsString(loginform);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/login")
				.contentType(MediaType.APPLICATION_JSON).content(loginformJson);
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		String body=mvcResult.getResponse().getContentAsString();
		assertEquals("Account is Locked", body);

	}
}
