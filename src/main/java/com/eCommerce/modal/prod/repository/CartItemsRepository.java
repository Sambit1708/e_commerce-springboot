package com.eCommerce.modal.prod.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eCommerce.modal.prod.CartItems;
import com.eCommerce.modal.prod.Product;
import com.eCommerce.modal.prod.Cart;
import com.eCommerce.modal.prod.ProductSize;
import java.util.List;


public interface CartItemsRepository extends JpaRepository<CartItems, String> {
	
	List<CartItems> findByCart(Cart cart);

	Optional<CartItems> findCartItemsByCartAndProduct(Cart cart, Product product);
	
	Optional<CartItems> findByIdAndCart(String id, Cart cart);
	
	Optional<CartItems> findCartItemsByCartAndProductAndProductSize(Cart cart, Product product, ProductSize productSize);
	
	Optional<CartItems> findCartItemsByIdAndProductAndProductSize(String id, Product product, ProductSize productSize);
}
