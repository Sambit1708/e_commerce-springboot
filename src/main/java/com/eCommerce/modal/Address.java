package com.eCommerce.modal;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.UuidGenerator;

import com.eCommerce.invoice.Invoice;
import com.eCommerce.modal.prod.Order;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {

	@Id
	@UuidGenerator
	private String id;
	private String address;
	private String pinCode;
	private String state;
	private String city;
	private String landMark;
	private AddressType addressType;
	private boolean currentAddress;
	
	private String deliverTo;
	private String phoneNumber;
	
	private LocalDateTime createDate;
	private LocalDateTime updateDate;
	
	@ManyToOne
	@JsonIgnore
	private User user;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "address")
	@JsonIgnore
	private List<Order> orders;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "address")
	@JsonIgnore
	private List<Invoice> invoices;

}
