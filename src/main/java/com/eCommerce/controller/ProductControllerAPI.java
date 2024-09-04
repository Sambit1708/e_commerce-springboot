package com.eCommerce.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.eCommerce.dto.ProductDto;
import com.eCommerce.dto.ProductSizeDto;
import com.eCommerce.modal.prod.Product;
import com.eCommerce.modal.prod.ProductSize;
import com.eCommerce.payload.ApiResponseMessage;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@CrossOrigin("*")
@RestController
@RequestMapping("/product")
@Tag(name = "Product")
public interface ProductControllerAPI {

	@PostMapping("/add")
	@Operation(
		description = "Post endpoint for adding Product",
		summary = "This api is used for adding Product into database",
		requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
				content = {@Content(schema = @Schema(implementation = ProductDto.class))}	
		),
		responses = { @ApiResponse(description = "Created", responseCode = "201")}
	)
	public ResponseEntity<Product> createProduct(@RequestBody ProductDto productDto);
	
	
	@GetMapping("/brand/{brandId}")
	@Operation(
		description = "Get endpoint for Product by Brand",
		summary = "This api is used for get product from database by brandId",
		parameters = {
			@Parameter(
				description = "Brand Id",
				required = true,
				in = ParameterIn.PATH
			)
		},
		responses = { @ApiResponse(description = "OK", responseCode = "200") }
	)
	public ResponseEntity<List<Product>> getAllProductOfBrand(@PathVariable("brandId") String brandId);
	
	
	@GetMapping("/{prodId}")
	@Operation(
		description = "Get endpoint for Product by Product",
		summary = "This api is used to get product from database",
		parameters = {
			@Parameter(
				description = "Product Id",
				required = true,
				in = ParameterIn.PATH
			)
		},
		responses = { @ApiResponse(description = "OK", responseCode = "200") }
	)
	public ResponseEntity<Product> getProduct(@PathVariable("prodId") String productId);
	

	@GetMapping("/getAll")
	@Operation(
		description = "Get endpoint for All Products",
		summary = "This api is used to get all the products from database",
		
		responses = { @ApiResponse(description = "OK", responseCode = "200") }
	)
	public ResponseEntity<List<Product>> getAllProduct();

	
	@PutMapping("/update-size")
	@Operation(
		description = "Post endpoint for updating Product Size",
		summary = "This api is used for updating Product Size into database",
		requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
				content = {@Content(schema = @Schema(implementation = ProductSizeDto.class))}	
		),
		responses = { @ApiResponse(description = "OK", responseCode = "201")}
	)
	public ResponseEntity<ApiResponseMessage> updateProductSize(
											@RequestParam("prodId") String prodId, 
											@RequestBody List<ProductSizeDto> productSizeDto);
	
	
	@PutMapping("/update")
	@Operation(
		description = "Post endpoint for updating Product Details",
		summary = "This api is used for updating Product Details into database",
		requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
				content = {@Content(schema = @Schema(implementation = ProductDto.class))}	
		),
		responses = { @ApiResponse(description = "OK", responseCode = "200")}
	)
	public ResponseEntity<ApiResponseMessage> updateProductDetail(
													@RequestParam("prodId") String prodId, 
													@RequestBody ProductDto productDto);

	
	@PostMapping(path="/add-img/{prodId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, 
								  produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(
			description = "Post endpoint for updating Product Image Path",
			summary = "This api is used for updating Product Image Name into database",
			requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
					content = {@Content(schema = @Schema(implementation = MultipartFile.class))}	
			),
			responses = { @ApiResponse(description = "OK", responseCode = "200")}
		)
	public ResponseEntity<ApiResponseMessage> addImage(
									  @RequestPart("files") MultipartFile[] files, 
									  @PathVariable("prodId") String prodId) throws Exception;
	
	
	@GetMapping(path = "/by-occasion")
	@Operation(
			description = "Get endpoint by occasion",
			summary = "This api is used to get distinct occasion from product details",
			responses = { @ApiResponse(description="OK", responseCode = "200", 
								 		content = @Content(mediaType = "application/json",
										schema =  @Schema(implementation = String.class))) 
						}
	)
	public ResponseEntity<List<String>> getAllDistinctOccasion();
	
	
	@GetMapping(path = "/by-idealFor")
	@Operation(
			description = "Get endpoint by idealFor",
			summary = "This api is used to get distinct ideal for from product details",
			responses = { @ApiResponse(description="OK", responseCode = "200", 
								 		content = @Content(mediaType = "application/json",
								 		schema =  @Schema(implementation = String.class))) 
						}
	)
	public ResponseEntity<List<String>> getAllDistinctIdealFor();
	
	
	@Operation(
			description = "Get endpoint by occasion",
			summary = "This api is used to get distinct type from product details",
			responses = { @ApiResponse(description="OK", responseCode = "200", 
										 content = @Content(mediaType = "application/json",
										 schema =  @Schema(implementation = String.class)))
						}
	)
	@GetMapping(path = "/by-type")
	public ResponseEntity<List<String>> getAllDistinctType();
	

	@Operation(
			description = "Get endpoint for Product Size by Product",
			summary = "This api is used to get product size by product",
			responses = { @ApiResponse(description="OK", responseCode = "200", 
										 content = @Content(mediaType = "application/json",
										 schema =  @Schema(implementation = ProductSize.class)))
						}
	)
	@GetMapping(path = "/sizes/{prodId}")
	public ResponseEntity<List<ProductSize>> getAllProductSizeOfProduct(@PathVariable("prodId") String productId);
}
