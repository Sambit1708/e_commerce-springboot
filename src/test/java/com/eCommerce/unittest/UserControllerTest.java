package com.eCommerce.unittest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import com.eCommerce.controller.impl.UserController;
import com.eCommerce.dataservice.UserGenerator;
import com.eCommerce.dto.UserDto;
import com.eCommerce.modal.Address;
import com.eCommerce.modal.service.impl.UserServiceImpl;

@ActiveProfiles("h2")
@DisplayName("User Service Test Case")
class UserControllerTest {
	
	@Mock
	private UserServiceImpl userService;
	
	@InjectMocks
	private UserController userController;
	
	UserGenerator userGenerator;
	
	private UserDto userDto;
	
	@BeforeEach
	void setUp() throws IOException  {
		MockitoAnnotations.openMocks(this);
		userGenerator = new UserGenerator();
		userDto = UserGenerator.readUserIdentityJsonFile("UserDto.json");
	}

	@Test
	public void CreateUser_Except_Success() {
		when(userService.createUserFlow(userDto)).thenReturn(userDto);
		
		ResponseEntity<UserDto> responce = this.userController.createUser(userDto);
		
		assertNotNull(responce.getBody());
		
		assertEquals(HttpStatus.CREATED, responce.getStatusCode());
	}

	@Test
	void UpdateUser_Except_Success() {
		when(userService.updateUser(userDto)).thenReturn(userDto);
		
		ResponseEntity<UserDto> responce = this.userController.updateUser(userDto);
		
		assertNotNull(responce.getBody());
		
		assertEquals(HttpStatus.CREATED, responce.getStatusCode());
	}
	
	@Test
	void getAllAddress_Except_Success() {
		List<Address> addresses = new ArrayList<>();
		when(userService.getAddressesByUser("sam@gmail.com")).thenReturn(addresses);
		
		Principal principal= () -> "sam@gmail.com";
		
		ResponseEntity<List<Address>> responce = this.userController.getAllAddressOfUser(principal);
		
		assertNotNull(responce.getBody());
		
		assertEquals(HttpStatus.OK, responce.getStatusCode());
	}
	
	@Test
	void getCurrentAddress_Except_Success() {
		Address address = new Address();
		when(userService.getCurrentAddress("sam@gmail.com")).thenReturn(address);
		
		Principal principal= () -> "sam@gmail.com";
		
		ResponseEntity<Address> responce = this.userController.getCurrentAddressOfUser(principal);
		
		assertNotNull(responce.getBody());
		
		assertEquals(HttpStatus.OK, responce.getStatusCode());
	}
}
