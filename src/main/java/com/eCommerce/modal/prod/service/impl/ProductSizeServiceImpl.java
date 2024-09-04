package com.eCommerce.modal.prod.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eCommerce.dto.ProductSizeDto;
import com.eCommerce.exception.ResourcesNotFoundException;
import com.eCommerce.modal.prod.Product;
import com.eCommerce.modal.prod.ProductSize;
import com.eCommerce.modal.prod.repository.ProductSizeRepository;
import com.eCommerce.modal.prod.service.ProductSizeService;

@Service
public class ProductSizeServiceImpl implements ProductSizeService {
	
	@Autowired
	private ProductSizeRepository productSizeRepository;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public ProductSize createProductSize(Product product, ProductSizeDto productSizeDto) {
		int size = productSizeDto.getSize();
		int quantity = productSizeDto.getQuantity();
		LocalDateTime nowDatTime = LocalDateTime.now();
		
		ProductSize productSize = new ProductSize(size, quantity, product);
		productSize.setCreateDate(nowDatTime);
		productSize.setUpdateDate(nowDatTime);
		productSize = this.productSizeRepository.save(productSize);
		return productSize;
	}

	@Override
	public List<ProductSize> updateProductSize(Product product, List<ProductSizeDto> productSizeDto) {
		List<ProductSize> oProductSize = this.productSizeRepository.findByProduct(product);
		List<ProductSize> productSizes = new ArrayList<>();
		if(oProductSize.isEmpty())
			return null;
		
		productSizeDto.forEach(item -> {
			ProductSize productSize = this.getProductSize(item.getId());
			if(item.getQuantity() > 0 && item.getQuantity() != productSize.getQuantity()) {
				productSize.setQuantity(item.getQuantity());
			}
			productSize.setUpdateDate(LocalDateTime.now());
			productSize = this.productSizeRepository.save(productSize);
			productSizes.add(productSize);
		});
		return productSizes;
	}

	@Override
	public List<ProductSizeDto> createMulProductSize(Product product, List<ProductSizeDto> productSizeDtos) {
		List<ProductSizeDto> productSizeDtoSol = new ArrayList<>();
		for(int i=0; i<productSizeDtos.size(); i++) {
			ProductSize productSize = 
					this.createProductSize(product, productSizeDtos.get(i));
			productSizeDtoSol.add(this.modelMapper.map(productSize, ProductSizeDto.class));
		}
		return productSizeDtoSol;
	}

	
	@Override
	public ProductSize getProductSize(String productSizeId) {

		ProductSize productSize = 
				this.productSizeRepository.findById(productSizeId).orElseThrow(
						() -> new ResourcesNotFoundException(
								"Product Size", "Product Size Id", productSizeId)
				);
		return productSize;
	}

	@Override
	public List<ProductSize> getAllProductSizeOfProduct(Product product) {
		List<ProductSize> productSize = this.productSizeRepository.findByProduct(product);
		return productSize;
	}

}
