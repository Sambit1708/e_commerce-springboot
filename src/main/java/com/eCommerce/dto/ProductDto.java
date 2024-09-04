package com.eCommerce.dto;

import java.util.ArrayList;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

	@NotBlank(message = "Title can not be blank")
	private String title;
	
	@Size(message = "Price can not be less than 50", min = 50)
	private double price;
	
	private String description;
	
	@NotNull(message = "Brand Id can not be blank")
	private String brandId;
	
	private List<ProductSizeDto> sizes = new ArrayList<>();
	
	@NotNull(message = "Color can not be blank")
	private String color;
	
	@NotNull(message = "Model Name can not be blank")
	private String modelName;
	
	@NotNull(message = "Ideal For can not be blank")
	private String idealFor;
	
	@NotNull(message = "Occasion can not be blank")
	private String occasion;
	
	@NotNull(message = "Product Type can not be blank")
	private String type;
}
