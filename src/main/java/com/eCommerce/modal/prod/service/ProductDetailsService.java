package com.eCommerce.modal.prod.service;

import java.util.List;

import com.eCommerce.dto.ProductDetailDto;
import com.eCommerce.modal.prod.Product;

public interface ProductDetailsService {

	public ProductDetailDto saveProductDetails(Product product, ProductDetailDto productDetailDto);
	
	public ProductDetailDto updateProductDetails(Product product, ProductDetailDto productDetailDto);
	
	public ProductDetailDto getProductDetails(Product product);
	
	public List<String> getAllDistinctOccasion();
	
	public List<String> getAllDistinctIdealFor();
	
	public List<String> getAllDistinctType();
}
