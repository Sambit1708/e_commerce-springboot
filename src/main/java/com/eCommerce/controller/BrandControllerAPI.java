package com.eCommerce.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eCommerce.dto.BrandDto;
import com.eCommerce.modal.prod.Brand;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@CrossOrigin("*")
@RestController
@RequestMapping("/brand")
@Tag(name = "Brand")
public interface BrandControllerAPI {

	@PostMapping("/add")
	@Operation(
			description = "Post endpoint for adding brand",
			summary = "This api is used for adding brand into database",
			requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
					content = {@Content(schema = @Schema(implementation = BrandDto.class))}	
			),
			responses = { @ApiResponse(description = "Created", responseCode = "201")}
	)
	public ResponseEntity<Brand> CreateRepository(@Valid @RequestBody BrandDto brandDto);
	
	@PutMapping("/update")
	@Operation(
		description = "Put endpoint for updating Address",
		summary = "This api is used for updating Address into database",
		requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
				content = {@Content(schema = @Schema(implementation = BrandDto.class))}),
		parameters = {
				@Parameter(
						description = "Id for Brand",
						required = true,
						in = ParameterIn.PATH
				)
		},
		responses = { @ApiResponse(description = "Created", responseCode = "201") }
	)
	public ResponseEntity<Brand> updateBrand(@RequestParam("brandId") String brandId, 
											@RequestBody BrandDto brandDto);
	
	
	@GetMapping("/get/{brandId}")
	@Operation(
		description = "Get endpoint for Brand",
		summary = "This api is used for get Brand from database by Id",
		parameters = {
			@Parameter(
				description = "Id for Brand",
				required = true,
				in = ParameterIn.PATH
			)
		},
		responses = { @ApiResponse(description = "OK", responseCode = "200") }
	)
	public ResponseEntity<Brand> getBrand(@PathVariable("brandId") String brandId);
	
	@GetMapping("/get")
	@Operation(
			description = "Get endpoint for all Brand",
			summary = "This api is used for get all Brand from database",
			responses = { @ApiResponse(description = "OK", responseCode = "200",
										content = @Content(mediaType = "application/json", 
										schema = @Schema(implementation = Brand.class)))
						}
	)
	public ResponseEntity<List<Brand>> getAllBrands();
}
