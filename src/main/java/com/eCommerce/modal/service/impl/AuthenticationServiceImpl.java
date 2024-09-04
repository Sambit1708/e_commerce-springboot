package com.eCommerce.modal.service.impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.eCommerce.jwt.JwtRequest;
import com.eCommerce.jwt.JwtResponse;
import com.eCommerce.jwt.JwtUtil;
import com.eCommerce.modal.User;
import com.eCommerce.modal.UserRoles;
import com.eCommerce.modal.service.AuthenticationService;
import com.eCommerce.modal.service.UserService;
import com.eCommerce.payload.ApiResponseMessage;
import com.eCommerce.payload.ECommerceConstant;
import com.eCommerce.payload.UserResponse;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserDetailsServiceImpl userDetailsService;
	
	@Autowired
	private UserService userService;

	@Autowired
	private JwtUtil jwtUtil;
	
	@Override
	public JwtResponse generateToken(JwtRequest jwtRequest) throws Exception {
		try {
			authenticate(jwtRequest.getUsername(), jwtRequest.getPassword());
		} catch (UsernameNotFoundException e)	{
			System.out.println("Username not found "+e.getMessage());
			throw new Exception("User not found.");
		}
		UserDetails userDetails = 
				this.userDetailsService.loadUserByUsername(jwtRequest.getUsername());
		String token = this.jwtUtil.generateToken(userDetails);

		return new JwtResponse(token);
	}

	private void authenticate(String username, String password) throws Exception {
		
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (Exception ex) {
			if(ex instanceof DisabledException) {
				throw new DisabledException("User has been disabled by admin");
			}
			else if(ex instanceof BadCredentialsException) {
				throw new DisabledException("User has been disabled by admin");
			}
			System.out.println(ex.getMessage());
		}
	}
	
	@Override
	public UserResponse getCurrentUser(String email) {
		UserResponse userResponse = UserResponse.builder().build();
		if(email == null || email == "") {
			return userResponse;
		}
		
		User currUser = (User) this.userDetailsService.loadUserByUsername(email);
		
		if(currUser != null) {
			Set<UserRoles> userRoles = this.userService.getUserRoles(currUser);
			Set<String> roles = new HashSet<>();
			userRoles.forEach(item -> {
				roles.add(item.getRole().getRoleName());
			});
			userResponse = 
					UserResponse.builder()
								.firstName(currUser.getFirstName())
								.lastName(currUser.getLastName())
								.email(currUser.getEmail())
								.gender(currUser.getGender())
								.phone(currUser.getPhone())
								.roles(roles)
								.build();
		}
		
		return userResponse;
	}

	@Override
	public ApiResponseMessage validateToken(JwtResponse jwtToken) {
		ApiResponseMessage apiResponseMessage = 
				ApiResponseMessage.builder()
									.status(false)
									.message(ECommerceConstant.INVALID_TOKEN_MSG)
									.statusCode(ECommerceConstant.INTERNAL_SERVER_ERROR)
									.build();
	
		String token = jwtToken.getToken();
		boolean validateToken = JwtUtil.isTokenExpired(token);
		
		if(!validateToken) {
			apiResponseMessage.setStatus(!validateToken);
			apiResponseMessage.setMessage(ECommerceConstant.VALID_TOKEN_MSG);
			apiResponseMessage.setStatusCode(ECommerceConstant.OK);
		}
		return apiResponseMessage;
	}

}
