package com.eCommerce.invoice;

import java.util.List;

import org.hibernate.annotations.UuidGenerator;

import com.eCommerce.modal.Address;
import com.eCommerce.modal.prod.Order;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Invoice {

	@Id
	@UuidGenerator
	private String id;

	private long invoiceNumber;
	
	private String customerName;
	
	private String invoiceDate;
	
	@OneToOne(mappedBy = "invoice")
	private Order order;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "invoice")
	List<InvoiceItems> invoiceItems;
	
	@ManyToOne
	private Address address;
	
	private String total;
}
