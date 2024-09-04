package com.eCommerce.modal.prod.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eCommerce.dto.BrandDto;
import com.eCommerce.exception.ResourcesNotFoundException;
import com.eCommerce.modal.prod.Brand;
import com.eCommerce.modal.prod.repository.BrandRepository;
import com.eCommerce.modal.prod.service.BrandService;

@Service
public class BrandServiceImpl implements BrandService {
	
	@Autowired
	private BrandRepository brandRepository;
	
	@Autowired
	private ModelMapper mapper;

	@Override
	public Brand createBrand(BrandDto brandDto) {
		Brand brand = this.mapper.map(brandDto, Brand.class);
		LocalDateTime nowDate = LocalDateTime.now();
		
		brand.setCreateDate(nowDate);
		brand.setUpdateDate(nowDate);
		return this.brandRepository.save(brand);
	}

	@Override
	public Brand updateBrand(String brandId, BrandDto brandDto) {
		Brand loadBrand = this.getBrandById(brandId);
		if(brandDto.getName() != null) {
			loadBrand.setName(brandDto.getName());
		}
		if(brandDto.getDescription() != null) {
			loadBrand.setDescription(brandDto.getDescription());
		}
		loadBrand.setUpdateDate(LocalDateTime.now());
		loadBrand = this.brandRepository.save(loadBrand);
		return loadBrand;
	}

	@Override
	public void deleteBrand(String brandId) {
		Brand brand = this.brandRepository.findById(brandId).orElseThrow(
						() -> new ResourcesNotFoundException("Brand", "Brand Id", brandId));
		this.brandRepository.delete(brand);
	}

	@Override
	public void deleteAllBrands() {
		this.brandRepository.deleteAll();
	}

	@Override
	public Brand getBrandById(String brandId) {
		Brand brand = this.brandRepository.findById(brandId).orElseThrow(
				() -> new ResourcesNotFoundException("Brand", "Brand Id", brandId)
		);
		return brand;
	}

	@Override
	public List<Brand> getAllBrands() {
		List<Brand> brands = this.brandRepository.findAll();
		return brands;
	}

}
