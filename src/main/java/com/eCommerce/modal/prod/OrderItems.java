package com.eCommerce.modal.prod;

import java.time.LocalDateTime;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class OrderItems {

	@Id
	@UuidGenerator
	private String id;
	
	private int quantity;
	
	private double price;
	
	private LocalDateTime createDate;
	
	private LocalDateTime updateDate;
	
	@ManyToOne
	private Product product;
	
	@ManyToOne
	private ProductSize productSize;
	
	@ManyToOne
	private Order order;
}
