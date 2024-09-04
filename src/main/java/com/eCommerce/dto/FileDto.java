package com.eCommerce.dto;

import org.springframework.http.MediaType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileDto {

	private String fileName;
	
	private MediaType fileType;
	
	private byte[] fileData;
}
