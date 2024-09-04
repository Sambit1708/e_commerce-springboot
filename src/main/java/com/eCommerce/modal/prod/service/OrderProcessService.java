package com.eCommerce.modal.prod.service;

import com.eCommerce.dto.OrderDto;

import jakarta.mail.MessagingException;

public interface OrderProcessService {

	public void processOrder(String email, OrderDto orderDto) throws MessagingException;
}
