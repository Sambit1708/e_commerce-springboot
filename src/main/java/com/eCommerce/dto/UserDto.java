package com.eCommerce.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserDto {

	@NotBlank(message = "First Name can not be blank")
	private String firstName;
	
	@NotBlank(message = "Last Name can not be blank")
	private String lastName;
	
	@Email(message = "Please enter a valid email")
	private String email;
	
	private String password;
	
	private String gender;
	
	private String phone;
	
	private boolean enabled;
}
