package com.eCommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceItemDto {

	private String description;
	
	private int quantity;
	
	private double unitPrice;
	
	private double deliveryCharge;
}
