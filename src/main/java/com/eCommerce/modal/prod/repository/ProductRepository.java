package com.eCommerce.modal.prod.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.eCommerce.modal.prod.Brand;
import com.eCommerce.modal.prod.Product;

public interface ProductRepository extends JpaRepository<Product, String> {

	public List<Product> findByBrand(Brand brand);
	
	@Query(value = "SELECT prod.id, prod.create_date, prod.description, prod.image_path, "
					+ "prod.price, prod.rating, prod.title, prod.update_date, prod.brand_id, "
					+ "prod.image_name FROM product AS prod JOIN product_details AS prod_dtls "
					+ "ON prod.id = prod_dtls.product_id WHERE MATCH(prod.title, prod.description) "
					+ "AGAINST (:searchItem IN NATURAL LANGUAGE MODE)OR MATCH(prod_dtls.color, "
					+ "prod_dtls.ideal_for, prod_dtls.model_name, prod_dtls.occasion, prod_dtls.type) "
					+ "AGAINST (:searchItem IN NATURAL LANGUAGE MODE);",
		   nativeQuery = true)
	public List<Product> searchProduct(@Param("searchItem") String searchItem);
}
