package com.eCommerce.modal.service;

import com.eCommerce.jwt.JwtRequest;
import com.eCommerce.jwt.JwtResponse;
import com.eCommerce.payload.ApiResponseMessage;
import com.eCommerce.payload.UserResponse;

public interface AuthenticationService {

	public JwtResponse generateToken(JwtRequest jwtRequest) throws Exception;
	
	public UserResponse getCurrentUser(String email);
	
	public ApiResponseMessage validateToken(JwtResponse jwtTokens);
}
