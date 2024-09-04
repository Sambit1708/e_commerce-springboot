package com.eCommerce.payload;

import java.util.Set;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {

	private String firstName;
	
	private String lastName;
	
	private String email;
	
	private String gender;
	
	private String phone;
	
	private Set<String> roles;
}
