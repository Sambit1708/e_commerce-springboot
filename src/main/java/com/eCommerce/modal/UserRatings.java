package com.eCommerce.modal;

import java.time.LocalDateTime;

import org.hibernate.annotations.UuidGenerator;

import com.eCommerce.modal.prod.Product;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="user_ratings")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRatings {

	@Id
	@UuidGenerator
	private String id;
	private int rating;
	private String review;
	
	private LocalDateTime createDate;
	private LocalDateTime updateDate;
	
	@ManyToOne(cascade = CascadeType.ALL)
	private Product product;
	
	@ManyToOne(cascade = CascadeType.ALL)
	private User user;
}
