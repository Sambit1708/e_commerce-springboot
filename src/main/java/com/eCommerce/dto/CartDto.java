package com.eCommerce.dto;

import com.eCommerce.modal.User;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CartDto {

	private User user;
	
	@Min(value = 1, message = "Minimum quantity should be 1")
	private int quantity;
	
	@NotBlank(message = "Product Id could not be null")
	private String productId;
	
	@NotBlank(message = "Product Size Id could not be null")
	private String productSizeId;
}
