package com.eCommerce.modal.prod.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.eCommerce.modal.prod.Product;
import com.eCommerce.modal.prod.ProductDetails;
import java.util.List;



public interface ProductDetailsRepository extends JpaRepository<ProductDetails, String> {

	Optional<ProductDetails> findByProduct(Product product);
	
	@Query(nativeQuery = true, value = "SELECT DISTINCT occasion FROM product_details")
	List<String> getDistinctOccasion();
	
	@Query(nativeQuery = true, value = "SELECT DISTINCT ideal_for FROM product_details")
	List<String> findAllDistinctIdealFor();
	
	@Query(nativeQuery = true, value = "SELECT DISTINCT type FROM product_details")
	List<String> findAllDistinctType();
}
