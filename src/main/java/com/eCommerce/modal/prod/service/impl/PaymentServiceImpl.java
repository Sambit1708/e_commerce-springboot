package com.eCommerce.modal.prod.service.impl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eCommerce.dto.PaymentDto;
import com.eCommerce.modal.User;
import com.eCommerce.modal.prod.Order;
import com.eCommerce.modal.prod.Payment;
import com.eCommerce.modal.prod.PaymentMode;
import com.eCommerce.modal.prod.PaymentStatus;
import com.eCommerce.modal.prod.repository.PaymentRepository;
import com.eCommerce.modal.prod.service.PaymentService;

@Service
public class PaymentServiceImpl implements PaymentService {
	
	@Autowired
	private PaymentRepository paymentRepository;

	@Override
	public Payment createPayment(User user, Order order, PaymentMode paymentMode) {
		Payment payment = new Payment();
		payment.setUser(user);
		payment.setOrder(order);
		payment.setAmount(String.valueOf(order.getTotalAmount()));
		payment.setPaymentMode(paymentMode);
		
		LocalDateTime now = LocalDateTime.now();
		payment.setCreateDate(now);
		payment.setUpdateDate(now);
		
		if(paymentMode == PaymentMode.CASH_ON_DELIVERY) {
			payment.setPaymentStatus(PaymentStatus.PENDING);
		}
		else {
			payment.setPaymentStatus(PaymentStatus.COMPLETED);
		}
		payment = this.paymentRepository.save(payment);
		
		return payment;
	}

	@Override
	public Payment changePaymentStatus(String paymentId, PaymentDto paymentDto) {
		// TODO Auto-generated method stub
		return null;
	}

}
