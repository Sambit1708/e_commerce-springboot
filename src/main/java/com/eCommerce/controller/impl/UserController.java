package com.eCommerce.controller.impl;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import com.eCommerce.controller.UserControllerAPI;
import com.eCommerce.dto.UserDto;
import com.eCommerce.modal.Address;
import com.eCommerce.modal.service.UserService;

@Controller
public class UserController implements UserControllerAPI {

	@Autowired
	private UserService userService;

	@Override
	public ResponseEntity<UserDto> createUser(UserDto userDto) {
		userDto = this.userService.createUserFlow(userDto);
		if(userDto == null) {
			return new ResponseEntity<>(userDto, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<UserDto>(userDto, HttpStatus.CREATED);
	}
	
	@Override
	public ResponseEntity<UserDto> updateUser(UserDto userDto) {
		userDto = this.userService.updateUser(userDto);
		return new ResponseEntity<UserDto>(userDto, HttpStatus.CREATED);
	}
	
	@Override
	public ResponseEntity<List<Address>> getAllAddressOfUser(Principal principal) {
		List<Address> address = this.userService.getAddressesByUser(principal.getName());
		if(address == null) {
			return new ResponseEntity<>(address, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(address, HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<Address> getCurrentAddressOfUser(Principal principal) {
		Address address = this.userService.getCurrentAddress(principal.getName());
		if(address == null) {
			return new ResponseEntity<>(address, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Address>(address, HttpStatus.OK);
	}

}
