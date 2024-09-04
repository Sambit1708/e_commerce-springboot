package com.eCommerce.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eCommerce.dto.FilterDto;
import com.eCommerce.modal.prod.Product;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@CrossOrigin("*")
@RestController
@RequestMapping("/search")
@Tag(name = "Search")
public interface SearchControllerAPI {

	@PostMapping("/filter")
	@Operation(
		description = "Post endpoint for search product",
		summary = "This api is used for searching product according to filter",
		requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
			content = {@Content(schema = @Schema(implementation = FilterDto.class))}	
		),
		responses = { @ApiResponse(description = "OK", responseCode = "200", 
									content = @Content(mediaType = "application/json", 
									schema = @Schema(implementation = Product.class)))
					}
	)
	public ResponseEntity<List<Product>> searchProductByFilter(@RequestBody FilterDto filterDto);
	
	
	@GetMapping(path = "/{search}")
	@Operation(
		description = "GET endpoint for search product",
		summary = "This api is used for searching product via name",
		parameters = {
				@Parameter(
					description = "Search",
					required = true,
					in = ParameterIn.PATH
				)
		},
		responses = { @ApiResponse(description = "OK", responseCode = "200", 
									content = @Content(mediaType = "application/json", 
									schema = @Schema(implementation = Product.class)))
					}
	)
	public ResponseEntity<List<Product>> Search(@PathVariable("search") String search);
}
