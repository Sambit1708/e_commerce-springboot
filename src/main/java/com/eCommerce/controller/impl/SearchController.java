package com.eCommerce.controller.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import com.eCommerce.controller.SearchControllerAPI;
import com.eCommerce.dto.FilterDto;
import com.eCommerce.modal.prod.Product;
import com.eCommerce.modal.service.SearchService;

@Controller
public class SearchController implements SearchControllerAPI {

	@Autowired
	private SearchService searchService;
	
	@Override
	public ResponseEntity<List<Product>> searchProductByFilter(FilterDto filterDto) {
		return null;
	}

	@Override
	public ResponseEntity<List<Product>> Search(String search) {
		List<Product> products = this.searchService.searchProdut(search);
		return ResponseEntity.ok(products);
	}
}
