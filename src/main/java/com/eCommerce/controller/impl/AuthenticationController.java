package com.eCommerce.controller.impl;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import com.eCommerce.controller.AuthenticationControllerAPI;
import com.eCommerce.jwt.JwtRequest;
import com.eCommerce.jwt.JwtResponse;
import com.eCommerce.modal.service.AuthenticationService;
import com.eCommerce.payload.ApiResponseMessage;
import com.eCommerce.payload.UserResponse;


@Controller
public class AuthenticationController implements AuthenticationControllerAPI {
	
	@Autowired
	private AuthenticationService authenticationService;
	
	//generate token
	@Override
	public ResponseEntity<?> generateToken(JwtRequest jwtRequest) throws Exception {
		
		JwtResponse jwtResponse = this.authenticationService.generateToken(jwtRequest);
		return new ResponseEntity<>(jwtResponse, HttpStatus.CREATED);
	}
	
	@Override
	public ResponseEntity<UserResponse> getCurrentUser(Principal principal) {
		
		UserResponse userResponse = this.authenticationService.getCurrentUser(principal.getName());
		return new ResponseEntity<>(userResponse, HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<ApiResponseMessage> validateToken(JwtResponse jwtToken) {
		
		ApiResponseMessage apiMessage = this.authenticationService.validateToken(jwtToken);
		return new ResponseEntity<ApiResponseMessage>(apiMessage, HttpStatus.OK);
	}
}
