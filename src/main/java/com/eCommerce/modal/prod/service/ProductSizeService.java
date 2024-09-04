package com.eCommerce.modal.prod.service;

import java.util.List;

import com.eCommerce.dto.ProductSizeDto;
import com.eCommerce.modal.prod.Product;
import com.eCommerce.modal.prod.ProductSize;

public interface ProductSizeService {

	public ProductSize createProductSize(Product product, ProductSizeDto productSizeDto);
	
	public ProductSize getProductSize(String productSizeId);
	
	public List<ProductSize> updateProductSize(Product product, List<ProductSizeDto> productSizeDto);
	
	public List<ProductSizeDto> createMulProductSize(Product product, List<ProductSizeDto> productSizeDtos);
	
	public List<ProductSize> getAllProductSizeOfProduct(Product product);
}
