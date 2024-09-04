package com.eCommerce.controller;

import java.security.Principal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eCommerce.dto.AddressDto;
import com.eCommerce.modal.Address;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@CrossOrigin("*")
@RestController
@RequestMapping("/user/address/")
@Tag(name="Address")
public interface AddressControllerAPI {

	
	@PostMapping("/add")
	@Operation(
			description = "Post endpoint for adding Address",
			summary = "This api is used for adding Address of user into database",
			requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
					content = {@Content(schema = @Schema(implementation = AddressDto.class))}),
			responses = {
					@ApiResponse(description = "Created", responseCode = "201"),
					@ApiResponse(description="Internal Server Error",responseCode = "500")
			}	
	)
	public ResponseEntity<Address> createAddress(Principal principal, @RequestBody AddressDto addressDto);
	
	@PutMapping("update")
	@Operation(
			description = "Put endpoint for updating Address",
			summary = "This api is used for updating Address into database",
			requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
					content = {@Content(schema = @Schema(implementation = AddressDto.class))}),
			parameters = {
					@Parameter(
							description = "Id for Address",
							required = true,
							in = ParameterIn.PATH
					)
			},
			responses = {
					@ApiResponse(description = "Created", responseCode = "201"),
					@ApiResponse(description="Internal Server Error",responseCode = "500")
			}
	)
	public ResponseEntity<Address> updateAddress(@RequestBody AddressDto addressDto, 
												 @RequestParam("addressId") String addrId) throws Exception;
	
	
	@DeleteMapping("delete/{addrId}")
	@Operation(
			description = "Delete endpoint for address",
			summary = "This api is used for deleting address from database by Id",
			parameters = {
					@Parameter(
							description = "Id for Address",
							required = true,
							in = ParameterIn.PATH
					)
			},
			responses = {
					@ApiResponse(description = "OK", responseCode = "200"),
					@ApiResponse(description="Bad request for api",responseCode = "400")
			}
	)
	public ResponseEntity<?> deleteAddressOfUser(@PathVariable("addrId") String addrId);
}
