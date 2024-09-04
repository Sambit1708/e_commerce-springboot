package com.eCommerce.modal.prod.service.impl;

import java.io.File;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.eCommerce.invoice.Invoice;
import com.eCommerce.modal.prod.service.FileService;
import com.eCommerce.utils.EmailService;
import com.eCommerce.utils.InvoicePdfGenerator;

import jakarta.mail.MessagingException;

@Service
public class FileServiceImpl implements FileService {

	@Autowired
	private Cloudinary cloudinary;
	
	@Autowired
	private InvoicePdfGenerator pdfGenerator;
	
	@Autowired
	private EmailService emailService;

	@SuppressWarnings("rawtypes")
	@Override
	public Map uploadImage2(MultipartFile file) {
		Map data = null;
		try {
			data = this.cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
		} catch(Exception ex) {
			throw new RuntimeException("Image Uploading failed !!");
		}
		return data;
	}


	@Async
	@Override
	public void generateAndSentInvoice(String email, Invoice invoice, String subject) throws MessagingException {
		File file = this.pdfGenerator.generatePDF(invoice);
		
		this.emailService.sendInvoiceEmailMessageWithAttachment(invoice, email, subject, file);
	}
	
	
}
