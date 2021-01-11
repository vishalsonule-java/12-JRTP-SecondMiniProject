package com.ashokit.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.ashokit.model.UnlockAccount;
import com.ashokit.properties.AppPropeties;
import com.ashokit.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest
public class UnlockAccountRestControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserService userService;
	
	@MockBean
	private AppPropeties appProperties;
	


	@Test
	public void test_1_handleUnLockAccountButton() throws Exception {
		UnlockAccount unlockAccount = new UnlockAccount();
		ObjectMapper mapper = new ObjectMapper();
		String unlockAccountObjJson = mapper.writeValueAsString(unlockAccount);
		when(userService.unlockAccount(unlockAccount)).thenReturn("account is allready unlcoked");
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/unlockAccount")
																																			.contentType(MediaType.APPLICATION_JSON)
																																			.content(unlockAccountObjJson);
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		String body = mvcResult.getResponse().getContentAsString();
		assertEquals("account is allready unlcoked", body);
		

	}
	
	@Test
	@Disabled
	public void test_2_handleUnLockAccountButton() throws Exception {
		UnlockAccount unlockAccount = new UnlockAccount();
		ObjectMapper mapper = new ObjectMapper();
		String unlockAccountObjJson = mapper.writeValueAsString(unlockAccount);
		when(userService.unlockAccount(unlockAccount)).thenReturn("account is unlcoked");
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/unlockAccount")
																																			.contentType(MediaType.APPLICATION_JSON)
																																			.content(unlockAccountObjJson);
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		String body = mvcResult.getResponse().getContentAsString();
		assertEquals("account is unlcoked", body);
		

	}
	
	@Test
	@Disabled
	public void test_3_handleUnLockAccountButton() throws Exception {
		UnlockAccount unlockAccount = new UnlockAccount();
		ObjectMapper mapper = new ObjectMapper();
		String unlockAccountObjJson = mapper.writeValueAsString(unlockAccount);
		when(userService.unlockAccount(unlockAccount)).thenReturn("password is miss match");
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/unlockAccount")
																																			.contentType(MediaType.APPLICATION_JSON)
																																			.content(unlockAccountObjJson);
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		String body = mvcResult.getResponse().getContentAsString();
		assertEquals("password is miss match", body);
		

	}
	
	@Test
	@Disabled
	public void test_4_handleUnLockAccountButton() throws Exception {
		UnlockAccount unlockAccount = new UnlockAccount();
		ObjectMapper mapper = new ObjectMapper();
		String unlockAccountObjJson = mapper.writeValueAsString(unlockAccount);
		when(userService.unlockAccount(unlockAccount)).thenReturn("login fail");
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/unlockAccount")
																																			.contentType(MediaType.APPLICATION_JSON)
																																			.content(unlockAccountObjJson);
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		String body = mvcResult.getResponse().getContentAsString();
		assertEquals("login fail", body);
		

	}
}
