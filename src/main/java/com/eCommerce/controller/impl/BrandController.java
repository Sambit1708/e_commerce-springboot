package com.eCommerce.controller.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import com.eCommerce.controller.BrandControllerAPI;
import com.eCommerce.dto.BrandDto;
import com.eCommerce.modal.prod.Brand;
import com.eCommerce.modal.prod.service.BrandService;


@Controller
public class BrandController implements BrandControllerAPI {

	@Autowired
	private BrandService brandSerice;
	
	@Override
	public ResponseEntity<Brand> CreateRepository(BrandDto brandDto) {
		Brand brandNew = this.brandSerice.createBrand(brandDto);
		return new ResponseEntity<>(brandNew, HttpStatus.CREATED);
	}
	
	
	@Override
	public ResponseEntity<Brand> updateBrand(String brandId, BrandDto brandDto) {
		Brand brand = this.brandSerice.updateBrand(brandId, brandDto);
		return new ResponseEntity<>(brand, HttpStatus.CREATED);
	}
	
	@Override
	public ResponseEntity<Brand> getBrand(String brandId) {
		Brand brand = this.brandSerice.getBrandById(brandId);
		return new ResponseEntity<Brand>(brand, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<List<Brand>> getAllBrands() {
		List<Brand> brand = this.brandSerice.getAllBrands();
		return ResponseEntity.ok(brand);
	}
}
