package com.eCommerce.utils;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.eCommerce.invoice.Invoice;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Component
public class EmailService {
	
	@Autowired
	private TemplateEngine templateEngine;
	
	@Autowired
    private JavaMailSender javaMailSender;

	
	public void sendInvoiceEmailMessageWithAttachment(Invoice invoice, String to, 
			String subject, File attachment) throws MessagingException {
		
		MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        
        Context context = new Context();
		context.setVariable("invoice", invoice);
		
		String htmlContent = templateEngine.process("invoice", context);
		
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlContent, true);
        
        FileSystemResource fileResource = new FileSystemResource(attachment);
        helper.addAttachment(fileResource.getFilename(), fileResource);

        javaMailSender.send(message);	}
}
