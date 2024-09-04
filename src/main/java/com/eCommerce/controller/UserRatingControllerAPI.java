package com.eCommerce.controller;

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

import com.eCommerce.dto.UserRatingDto;
import com.eCommerce.modal.UserRatings;

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
@RequestMapping("/user/rating")
@Tag(name = "User Ratings")
public interface UserRatingControllerAPI {

	@PostMapping("/create")
	@Operation(
		description = "Post endpoint for adding User's rating",
		summary = "This api is used for adding UserRating into database",
		requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
			content = {@Content(schema = @Schema(implementation = UserRatingDto.class))}	
		),
		responses = { @ApiResponse(description = "Created", responseCode = "201")}
	)
	public ResponseEntity<UserRatings> createUserRating(@Valid @RequestBody UserRatingDto userRatingDto);
	
	@PutMapping("/update")
	@Operation(
		description = "Put endpoint for updating UserRating",
		summary = "This api is used for updating UserRating into database",
		requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
			content = {@Content(schema = @Schema(implementation = UserRatingDto.class))}),
		parameters = {
			@Parameter(
					description = "Id for UserRaing",
					required = true,
					in = ParameterIn.PATH
			)
		},
		responses = { @ApiResponse(description = "Created", responseCode = "201") }
	)
	public ResponseEntity<UserRatings> updateUserRating(@RequestBody UserRatingDto userRatingDto, 
														@RequestParam("userRatingId") String userRatingId);
	
	@GetMapping("/forProd/{prodId}")
	@Operation(
		description = "Get endpoint for UserRating",
		summary = "This api is used for get UserRating from database by Id",
		parameters = {
			@Parameter(
				description = "Id for UserRating",
				required = true,
				in = ParameterIn.PATH
			)
		},
		responses = { @ApiResponse(description = "OK", responseCode = "200") }
	)
	public ResponseEntity<?> getRatingsForProduct(@PathVariable("prodId") String prodId);
}
