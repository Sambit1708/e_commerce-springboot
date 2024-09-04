package com.eCommerce.invoice;

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
public class InvoiceItems {

	@Id
	@UuidGenerator
	private String id;
	
	private String description;
	
	private int quantity;
	
	private double unitPrice;
	
	private double deliveryCharge;
	
	@ManyToOne
	private Invoice invoice;
}
