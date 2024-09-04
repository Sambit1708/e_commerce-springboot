package com.eCommerce.modal.prod.service;

import com.eCommerce.dto.PaymentDto;
import com.eCommerce.modal.User;
import com.eCommerce.modal.prod.Order;
import com.eCommerce.modal.prod.Payment;
import com.eCommerce.modal.prod.PaymentMode;

public interface PaymentService {

	public Payment createPayment(User user, Order order, PaymentMode paymentMode);
	
	public Payment changePaymentStatus(String paymentId, PaymentDto paymentDto);
}
