package com.eCommerce.modal.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.eCommerce.modal.service.CloudinaryImageService;

@Service
public class CloudinaryImageServiceImpl implements CloudinaryImageService {
	
	@Autowired
	private Cloudinary cloudinary;

	@SuppressWarnings("rawtypes")
	@Override
	public Map upload(MultipartFile file) {
		Map data = null;
		try {
			data = this.cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
		} catch(Exception ex) {
			throw new RuntimeException("Image Uploading failed !!");
		}
		return data;
	}

}
