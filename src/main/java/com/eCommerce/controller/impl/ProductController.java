package com.eCommerce.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

import com.eCommerce.controller.ProductControllerAPI;
import com.eCommerce.dto.ProductDto;
import com.eCommerce.dto.ProductSizeDto;
import com.eCommerce.modal.prod.Product;
import com.eCommerce.modal.prod.ProductSize;
import com.eCommerce.modal.prod.service.ProductDetailsService;
import com.eCommerce.modal.prod.service.ProductService;
import com.eCommerce.payload.ApiResponseMessage;
import com.eCommerce.payload.ECommerceConstant;

import java.util.List;


@Controller
public class ProductController implements ProductControllerAPI {

	@Autowired
	private ProductService productService;
	
	@Autowired
	private ProductDetailsService productDetailsService;
	

	@Override
	public ResponseEntity<Product> createProduct(ProductDto productDto) {
		
		Product product = this.productService.createProduct(productDto);
		return new ResponseEntity<>(product, HttpStatus.CREATED);
	}
	
	@Override
	public ResponseEntity<List<Product>> getAllProductOfBrand(String brandId) {
		return ResponseEntity.ok(this.productService.getProductsByBrand(brandId));
	}

	@Override
	public ResponseEntity<Product> getProduct(String productId) {
		Product product = this.productService.getProduct(productId);
		return new ResponseEntity<>(product, HttpStatus.OK);
		
	}
	
	@Override
	public ResponseEntity<List<Product>> getAllProduct() {
		List<Product> products = this.productService.getProducts();
		return ResponseEntity.ok(products);	
	}
	
	@Override
	public ResponseEntity<ApiResponseMessage> updateProductSize(String prodId, List<ProductSizeDto> productSizeDto) {
		
		ApiResponseMessage apiMessage = 
				this.productService.updateProductSize(prodId, productSizeDto);
		return new ResponseEntity<ApiResponseMessage>(apiMessage, HttpStatus.CREATED);
	}
	
	@Override
	public ResponseEntity<ApiResponseMessage> updateProductDetail(String prodId, ProductDto productDto) {
		
		ApiResponseMessage apiMessage = 
				this.productService.updateProductDetails(prodId, productDto);
		return new ResponseEntity<ApiResponseMessage>(apiMessage, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<ApiResponseMessage> addImage(MultipartFile[] files, String prodId) throws Exception 
	{
		@SuppressWarnings("unused")
		Product product = this.productService.addImageToProduct(prodId, files[0]);
		ApiResponseMessage apiResponseMessage = 
								ApiResponseMessage.builder()
											.status(true)
											.message(ECommerceConstant.IMAGE_ADDED_SUCCESS_MSG)
											.statusCode(ECommerceConstant.CREATED)
											.build();
		return new ResponseEntity<>(apiResponseMessage, HttpStatus.CREATED);
	}
	
	@Override
	public ResponseEntity<List<String>> getAllDistinctOccasion() {
		List<String> occasions = this.productDetailsService.getAllDistinctOccasion();
		return ResponseEntity.ok(occasions);
	}
	
	@Override
	public ResponseEntity<List<String>> getAllDistinctIdealFor() {
		List<String> idelFors = this.productDetailsService.getAllDistinctIdealFor();
		return ResponseEntity.ok(idelFors);
	}
	
	@Override
	public ResponseEntity<List<String>> getAllDistinctType() {
		List<String> types = this.productDetailsService.getAllDistinctType();
		return ResponseEntity.ok(types);
	}

	@Override
	public ResponseEntity<List<ProductSize>> getAllProductSizeOfProduct(String productId) {
		List<ProductSize> productSizes = 
				this.productService.getAllProductSizeOfProduct(productId);
		return ResponseEntity.ok(productSizes);
	}
	
}
