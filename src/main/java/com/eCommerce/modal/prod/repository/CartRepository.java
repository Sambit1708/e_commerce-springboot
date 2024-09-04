package com.eCommerce.modal.prod.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eCommerce.modal.prod.Cart;
import com.eCommerce.modal.User;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, String> {

	Optional<Cart> findByUser(User user);
	
	Optional<Cart> findByIdAndUser(String id, User user);
}
