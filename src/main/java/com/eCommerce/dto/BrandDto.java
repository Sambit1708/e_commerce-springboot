package com.eCommerce.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BrandDto {

	@NotBlank(message = "Brand Name could not be blank")
	private String name;
	
	private String description;
}
