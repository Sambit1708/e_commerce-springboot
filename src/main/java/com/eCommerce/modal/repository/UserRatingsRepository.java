package com.eCommerce.modal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eCommerce.modal.User;
import com.eCommerce.modal.UserRatings;
import com.eCommerce.modal.prod.Product;

public interface UserRatingsRepository extends JpaRepository<UserRatings, String> {
	
	public List<UserRatings> findByProduct(Product product);
	
	UserRatings findByProductAndUser(Product product, User user);
}
