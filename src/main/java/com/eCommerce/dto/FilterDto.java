package com.eCommerce.dto;

import java.util.List;

import lombok.Data;

@Data
public class FilterDto {

	private List<String> idealFor;
	
	private List<String> brand;
	
	private List<String> occasion;
	
	private List<String> type;
}
