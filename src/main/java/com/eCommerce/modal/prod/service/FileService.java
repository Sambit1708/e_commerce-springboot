package com.eCommerce.modal.prod.service;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.eCommerce.invoice.Invoice;

import jakarta.mail.MessagingException;


public interface FileService {
	
	@SuppressWarnings("rawtypes")
	public Map uploadImage2(MultipartFile file);
	
	public void generateAndSentInvoice(String email, Invoice invoice, String subject) throws MessagingException;
}
