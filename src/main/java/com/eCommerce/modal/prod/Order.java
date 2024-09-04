package com.eCommerce.modal.prod;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.UuidGenerator;

import com.eCommerce.invoice.Invoice;
import com.eCommerce.modal.Address;
import com.eCommerce.modal.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Orders")
public class Order {

	@Id
	@UuidGenerator
	private String id;
	
	private double totalAmount;
	
	private OrderStatus orderStatus;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	private User user;
	
	@ManyToOne
	private Address address;
	
	private LocalDateTime createdDate;
	
	private LocalDateTime updateDate;
	
	private LocalDateTime deliveryDate;

	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<OrderItems> orderItems;
	
	@OneToOne
	@JoinColumn(name="invoice_id", referencedColumnName = "id")
	@JsonIgnore
	private Invoice invoice;
}
