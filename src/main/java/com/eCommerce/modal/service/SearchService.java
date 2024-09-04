package com.eCommerce.modal.service;

import java.util.List;

import com.eCommerce.dto.FilterDto;
import com.eCommerce.modal.prod.Product;

public interface SearchService {

	public List<Product> searchProductByFilter(FilterDto filterDto);
	
	List<Product> searchProdut(String search);
}
