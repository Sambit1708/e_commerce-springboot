package com.eCommerce.modal.prod.service;

import java.util.List;

import com.eCommerce.dto.BrandDto;
import com.eCommerce.modal.prod.Brand;

public interface BrandService {

	public Brand createBrand(BrandDto brandDto);
	
	public Brand updateBrand(String brandId, BrandDto brandDto);
	
	public void deleteBrand(String brandId);
	
	public void deleteAllBrands();
	
	public Brand getBrandById(String brandId);
	
	public List<Brand> getAllBrands();
}
