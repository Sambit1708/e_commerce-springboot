package com.eCommerce.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDetailDto {

	@NotNull(message = "Color can not be blank")
	private String color;
	
	@NotNull(message = "Model Name can not be blank")
	private String modelName;
	
	@NotNull(message = "Ideal For can not be blank")
	private String idealFor;
	
	@NotNull(message = "Occasion can not be blank")
	private String occasion;
	
	@NotNull(message = "Shoes Type can not be blank")
	private String type;
}
