package com.eCommerce.modal.service;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

@SuppressWarnings("rawtypes")
public interface CloudinaryImageService {

	public Map upload(MultipartFile file);
}
