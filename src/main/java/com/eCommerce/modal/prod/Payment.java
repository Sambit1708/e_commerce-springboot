package com.eCommerce.modal.prod;

import java.time.LocalDateTime;

import org.hibernate.annotations.UuidGenerator;

import com.eCommerce.modal.User;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
@Entity
public class Payment {

	@Id
	@UuidGenerator
	private String id;
	
	@OneToOne
	@JoinColumn(name="order_id", referencedColumnName = "id")
	private Order order;
	
	private PaymentMode paymentMode;
	
	private LocalDateTime createDate;
	
	private LocalDateTime updateDate;
	
	private PaymentStatus paymentStatus;
	
	private String amount;
	
	@ManyToOne
	private User user;
}
