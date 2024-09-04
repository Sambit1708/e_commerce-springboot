package com.eCommerce.dto;

import java.util.List;

import com.eCommerce.modal.prod.PaymentMode;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
	
	private String addressId;
	
	private PaymentMode paymentMode;
	
	private List<String> cartItemIds;
	
	private List<OrderItemsDto> orderItemsDtos;
}
