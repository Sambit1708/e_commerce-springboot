package com.eCommerce.dto;

import com.eCommerce.modal.AddressType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDto {

	@NotBlank(message = "Address field can't blank")
	private String address;
	
	@Pattern(regexp = "^\\d{6}$", message = "Enter a valid adress")
	private String pinCode;
	
	@NotBlank(message = "State field can't blank")
	private String state;
	
	@NotBlank(message = "City field can't blank")
	private String city;

	private String landMark;
	
	private AddressType addressType;
	
	private String deliverTo;
	
	@Pattern(regexp = "^(\\+91[\\-\\s]?)?[0]?(91)?[789]\\d{9}$", message = "Please add a valid address")
	private String phoneNumber;
}
