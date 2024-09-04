package com.eCommerce.modal.prod;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.hibernate.annotations.UuidGenerator;

import com.eCommerce.modal.User;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Cart {

	@Id
	@UuidGenerator
	private String id;
	
	@OneToOne
	@JoinColumn(name="user_id", referencedColumnName = "id")
	private User user;
	
	private LocalDateTime createDate;
	
	private LocalDateTime updateDate;
	
	private double total;
	
	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	@OneToMany(mappedBy = "cart")
	private final List<CartItems> cartDetails = new ArrayList<>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public LocalDateTime getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDateTime createDate) {
		this.createDate = createDate;
	}

	public LocalDateTime getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(LocalDateTime updateDate) {
		this.updateDate = updateDate;
	}

	public List<CartItems> getCartDetails() {
		return cartDetails;
	}
}
