package com.eCommerce.dataservice;

import java.io.File;
import java.io.IOException;

import com.eCommerce.dto.UserDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class UserGenerator {

	public static UserDto readUserIdentityJsonFile(String filePath) throws IOException {
		File file = new File("src//test//resources//expected-data//"+filePath);
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.readValue(file, new TypeReference<UserDto>() {
		});
	}
}
