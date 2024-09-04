package com.eCommerce.modal.prod.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eCommerce.dto.InvoiceDto;
import com.eCommerce.dto.OrderDto;
import com.eCommerce.invoice.Invoice;
import com.eCommerce.invoice.InvoiceService;
import com.eCommerce.modal.prod.Order;
import com.eCommerce.modal.prod.service.FileService;
import com.eCommerce.modal.prod.service.OrderProcessService;
import com.eCommerce.modal.prod.service.OrderService;

import jakarta.mail.MessagingException;

@Service
public class OrderProcessServiceImpl implements OrderProcessService {

	@Autowired
	private OrderService orderService;
	
	@Autowired
	private InvoiceService invoiceService;
	
	@Autowired
	private FileService fileService;
	
	@Override
	public void processOrder(String email, OrderDto orderDto) throws MessagingException {

		// Create Order
		Order order = 
				this.orderService.order(email, orderDto);
		
		// Prepare InvoiceDto
		InvoiceDto invoiceDto = 
				this.invoiceService.prepareInvoice(email, order);
		
		// Create Invoice
		Invoice invoice = 
				this.invoiceService.generateInvoice(invoiceDto);
		
		this.fileService
				.generateAndSentInvoice("sambitvolt.2000@gmail.com", invoice, "Invoice For Your Order Id: "+invoice.getId());
	}

}
