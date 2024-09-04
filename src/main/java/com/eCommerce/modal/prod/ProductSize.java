package com.eCommerce.modal.prod;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.UuidGenerator;

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
public class ProductSize {

	@Id
	@UuidGenerator
	private String id;
	
	private int size;
	
	private int quantity;
	
	private LocalDateTime createDate;
	
	private LocalDateTime updateDate;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonIgnore
	private Product product;
	
	@OneToMany(mappedBy = "productSize", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<CartItems> cartItems;
	
	@OneToMany(mappedBy = "productSize", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<OrderItems> orderItems;

	public ProductSize(int size, int quantity, Product product) {
		super();
		this.size = size;
		this.quantity = quantity;
		this.product = product;
	}
}
