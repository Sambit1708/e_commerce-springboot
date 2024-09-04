package com.eCommerce.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eCommerce.dto.UserDto;
import com.eCommerce.modal.Address;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@CrossOrigin("*")
@RestController
@RequestMapping("/user")
@Tag(name = "User")
public interface UserControllerAPI {
	
	@PostMapping("/create-user")
	@Operation(
		description = "Post endpoint for adding User",
		summary = "This api is used for adding User into database",
		requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
			content = {@Content(schema = @Schema(implementation = UserDto.class))}	
		),
		responses = { @ApiResponse(description = "Created", responseCode = "201", 
									content = @Content(mediaType = "application/json", 
									schema = @Schema(implementation = UserDto.class)))
					}
	)
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto);

	
	@PutMapping("/update-user")
	@Operation(
		description = "Post endpoint for update User",
		summary = "This api is used for updating User into database",
		requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
			content = {@Content(schema = @Schema(implementation = UserDto.class))}	
		),
		responses = { @ApiResponse(description = "Created", responseCode = "201")}
	)
	public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto);
	
	
	@GetMapping("/address/getAll")
	@Operation(
		description = "Get endpoint for address",
		summary = "This api is used for get address from database by Id",
		responses = { @ApiResponse(description = "OK", responseCode = "200",
								  	content = @Content(mediaType = "application/json",
								  	schema = @Schema(implementation = Address.class))
								  ) 
								
					}
	)
	public ResponseEntity<List<Address>> getAllAddressOfUser(Principal principal);
	
	
	@GetMapping("/address/current")
	@Operation(
		description = "Get endpoint for address",
		summary = "This api is used for get address from database by Id",
		responses = { @ApiResponse(description = "OK", responseCode = "200", content = @Content(mediaType = "application/json",
								  	schema = @Schema(implementation = Address.class))
								  ),
					  @ApiResponse(description = "NOT_FOUND", responseCode = "404", content = @Content(mediaType = "application/json",
								  	schema = @Schema(implementation = Address.class))
							  	  )		
					}
	)
	public ResponseEntity<Address> getCurrentAddressOfUser(Principal principal);

}