package com.eCommerce.modal.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eCommerce.dto.FilterDto;
import com.eCommerce.modal.prod.Product;
import com.eCommerce.modal.prod.repository.ProductRepository;
import com.eCommerce.modal.service.SearchService;

@Service
public class SearchServiceImpl implements SearchService {

	@Autowired
	private ProductRepository productRepository;
	
	@Override
	public List<Product> searchProductByFilter(FilterDto filterDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Product> searchProdut(String search) {
		search = "+"+search;
		List<Product> products = this.productRepository.searchProduct(search);
		return products;
	}

}
