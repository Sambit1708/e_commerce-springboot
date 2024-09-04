package com.eCommerce.dto;

import com.eCommerce.modal.prod.PaymentMode;
import com.eCommerce.modal.prod.PaymentStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDto {

	private String userId;
	
	private String orderId;
	
	private PaymentMode paymentMode;
	
	private PaymentStatus paymentStatus;
}
