package com.eCommerce.modal.prod.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eCommerce.modal.prod.Product;
import com.eCommerce.modal.prod.ProductSize;


public interface ProductSizeRepository extends JpaRepository<ProductSize, String> {

	List<ProductSize> findByProduct(Product product);
}
