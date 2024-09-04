package com.eCommerce.modal.prod.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.eCommerce.dto.ProductDto;
import com.eCommerce.dto.ProductSizeDto;
import com.eCommerce.modal.prod.Product;
import com.eCommerce.modal.prod.ProductSize;
import com.eCommerce.payload.ApiResponseMessage;

public interface ProductService {

	public Product createProduct(ProductDto productDto);
	
	public Product addImageToProduct(String prodId, MultipartFile file) throws IOException;
	
	public ApiResponseMessage updateProductSize(String productId, List<ProductSizeDto> productSizeDto);
	
	public ApiResponseMessage updateProductDetails(String productId, ProductDto productDto);
	
	public List<Product> getProducts();
	
	public List<Product> getProductsByBrand(String brandId);
	
	public Product getProduct(String productId);
	
	public void deleteProduct(String productId);
	
	public void deleteAllProducts();
	
	public List<ProductSize> getAllProductSizeOfProduct(String productId);
}
