package com.eCommerce.modal.prod.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eCommerce.dto.ProductDetailDto;
import com.eCommerce.modal.prod.Product;
import com.eCommerce.modal.prod.ProductDetails;
import com.eCommerce.modal.prod.repository.ProductDetailsRepository;
import com.eCommerce.modal.prod.service.ProductDetailsService;

@Service
public class ProductDetailsServiceImpl implements ProductDetailsService {
	
	@Autowired
	private ProductDetailsRepository productDetailsRepository;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public ProductDetailDto saveProductDetails(Product product, ProductDetailDto productDetailDto) {
		ProductDetails productDetails = 
				this.modelMapper.map(productDetailDto, ProductDetails.class);
		LocalDateTime nowDateTime = LocalDateTime.now();
		
		productDetails.setCreateDate(nowDateTime);
		productDetails.setUpdateDate(nowDateTime);
		productDetails.setProduct(product);
		
		productDetails = this.productDetailsRepository.save(productDetails);
		productDetailDto = this.modelMapper.map(productDetails, ProductDetailDto.class);
		return productDetailDto;
	}

	
	@Override
	public ProductDetailDto updateProductDetails(Product product, ProductDetailDto productDetailDto) {
		Optional<ProductDetails> pDetail = this.productDetailsRepository.findByProduct(product);
		ProductDetails productDetails = pDetail.isEmpty() ? null : pDetail.get();
		if(pDetail.isEmpty())
			return null;
		
		if(productDetailDto.getColor() != null) {
			productDetails.setColor(productDetailDto.getColor());
		}
		
		if(productDetailDto.getIdealFor() != null) {
			productDetails.setIdealFor(productDetailDto.getIdealFor());
		}
		
		if(productDetailDto.getModelName() != null) {
			productDetails.setModelName(productDetailDto.getModelName());
		}
		
		if(productDetailDto.getOccasion() != null) {
			productDetails.setOccasion(productDetailDto.getOccasion());
		}
		
		if(productDetailDto.getType() != null) {
			productDetails.setType(productDetailDto.getType());
		}
		productDetails.setUpdateDate(LocalDateTime.now());
		productDetails = this.productDetailsRepository.save(productDetails);
		productDetailDto = this.modelMapper.map(productDetails, ProductDetailDto.class);
		
		return productDetailDto;
	}

	
	public ProductDetailDto getProductDetails(Product product) {
		Optional<ProductDetails> productDetail = 
				this.productDetailsRepository.findByProduct(product);
		ProductDetails productDetails = (productDetail.isEmpty()) ? null 
																  : productDetail.get();
		
		ProductDetailDto productDetailDto = 
				this.modelMapper.map(productDetails, ProductDetailDto.class);
		
		return productDetailDto;
	}

	
	@Override
	public List<String> getAllDistinctOccasion() {
		List<String> occasions = 
				this.productDetailsRepository.getDistinctOccasion();
		
		return occasions;
	}

	
	@Override
	public List<String> getAllDistinctIdealFor() {
		List<String> idealFors = 
				this.productDetailsRepository.findAllDistinctIdealFor();
		
		return idealFors;
	}

	
	@Override
	public List<String> getAllDistinctType() {
		List<String> types = 
				this.productDetailsRepository.findAllDistinctType();

		return types;
	}
}
