package com.eCommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemsDto {
	
	private int quantity;
	
	private double price;
	
	private String productId;
	
	private String productSizeId;
}
