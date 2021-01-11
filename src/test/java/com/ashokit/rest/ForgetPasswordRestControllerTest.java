package com.ashokit.rest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.ashokit.properties.AppPropeties;
import com.ashokit.services.UserService;

@WebMvcTest
public class ForgetPasswordRestControllerTest {
	
	@MockBean
	private UserService userService;
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private AppPropeties appProperties;
	
	@Test
	public void test_handleForgetPasswordButton() throws Exception {
		when(userService.recoverPassword("vishalsonule2@gmail.com")).thenReturn("recover password successfully");
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/forgetPassword").param("email", "vishalsonule2@gmail.com");
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		
	}

}
