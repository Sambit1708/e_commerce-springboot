package com.eCommerce.modal.prod.service.impl;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.eCommerce.dto.ProductDetailDto;
import com.eCommerce.dto.ProductDto;
import com.eCommerce.dto.ProductSizeDto;
import com.eCommerce.exception.ResourcesNotFoundException;
import com.eCommerce.modal.prod.Brand;
import com.eCommerce.modal.prod.Product;
import com.eCommerce.modal.prod.ProductSize;
import com.eCommerce.modal.prod.repository.ProductRepository;
import com.eCommerce.modal.prod.service.BrandService;
import com.eCommerce.modal.prod.service.FileService;
import com.eCommerce.modal.prod.service.ProductDetailsService;
import com.eCommerce.modal.prod.service.ProductService;
import com.eCommerce.modal.prod.service.ProductSizeService;
import com.eCommerce.payload.ApiResponseMessage;
import com.eCommerce.payload.ECommerceConstant;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private ProductSizeService productSizeService;
	
	@Autowired
	private BrandService brandService;
	
	@Autowired
	private ProductDetailsService productDetailsService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private FileService fileService;
	
	
	Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

	@Override
	public Product createProduct(ProductDto productDto) {
		Product product = modelMapper.map(productDto, Product.class);
		Brand brand = this.brandService.getBrandById(productDto.getBrandId());
		product.setBrand(brand);
		
		LocalDateTime nowDate = LocalDateTime.now();
		
		product.setCreateDate(nowDate);
		product.setUpdateDate(nowDate);
		
		final Product saveProduct = this.productRepository.save(product);
		
		this.productSizeService.createMulProductSize(saveProduct, productDto.getSizes());
		
		ProductDetailDto pDetailDto = this.modelMapper.map(productDto, ProductDetailDto.class);
		productDetailsService.saveProductDetails(saveProduct, pDetailDto);
		
		return saveProduct;
	}

	@Override
	public ApiResponseMessage updateProductSize(String prodId, List<ProductSizeDto> productSizeDtos) {
		
		Product loadProduct = this.getProduct(prodId);
		loadProduct.setUpdateDate(LocalDateTime.now());
		
		ApiResponseMessage apiMessage = ApiResponseMessage.builder()
											.status(false)
											.message(ECommerceConstant.PROBLEM_MSG)
											.statusCode(ECommerceConstant.INTERNAL_SERVER_ERROR)
											.build();
		List<ProductSize> productSize =  
				this.productSizeService.updateProductSize(loadProduct, productSizeDtos);
		if(productSize == null)
			return apiMessage;
		
		apiMessage = ApiResponseMessage.builder()
										.status(true)
										.message(ECommerceConstant.SIZE_QTY_UPDATE_MSG)
										.statusCode(ECommerceConstant.CREATED)
										.build();
		return apiMessage;
	}
	
	@Override
	public ApiResponseMessage updateProductDetails(String prodId, ProductDto productDto) {
		
		Product loadProduct = this.getProduct(prodId);
		loadProduct.setUpdateDate(LocalDateTime.now());
		
		ApiResponseMessage apiMessage = ApiResponseMessage.builder()
											.status(false)
											.message(ECommerceConstant.PROBLEM_MSG)
											.statusCode(ECommerceConstant.INTERNAL_SERVER_ERROR)
											.build();
		
		ProductDetailDto productDetailDto = this.modelMapper.map(productDto, ProductDetailDto.class);

		productDetailDto =  
				this.productDetailsService.updateProductDetails(loadProduct, productDetailDto);
		if(productDetailDto == null)
			return apiMessage;
		
		apiMessage = ApiResponseMessage.builder()
										.status(true)
										.message(ECommerceConstant.PRODUCT_DETAIL_UPDATE_MSG)
										.statusCode(ECommerceConstant.CREATED)
										.build();
		return apiMessage;
	}

	@Override
	public List<Product> getProducts() {
		List<Product> products = this.productRepository.findAll();
		return products;
	}

	@Override
	public List<Product> getProductsByBrand(String brandId) {
		Brand brand = this.brandService.getBrandById(brandId);
		return this.productRepository.findByBrand(brand);
	}

	@Override
	public Product getProduct(String productId) {
		Product product = this.productRepository.findById(productId).orElseThrow(
				() 	-> new ResourcesNotFoundException("Product", "Product Id", productId)
		);
		return product;
	}

	@Override
	public void deleteProduct(String productId) {
		Product product = this.getProduct(productId);
		this.productRepository.delete(product);
	}

	@Override
	public void deleteAllProducts() {
		this.productRepository.deleteAll();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Product addImageToProduct(String prodId, MultipartFile file) throws IOException {
		Product product = this.getProduct(prodId);
		Map imgData = this.fileService.uploadImage2(file);
		product.setImageName(file.getOriginalFilename());
		product.setImagePath(imgData.get("url").toString());
		this.productRepository.save(product);
		return product;
	}

	@Override
	public List<ProductSize> getAllProductSizeOfProduct(String productId) {
		Product product = this.getProduct(productId);
		List<ProductSize> productSizes = 
				this.productSizeService.getAllProductSizeOfProduct(product);
		return productSizes;
	}
}
