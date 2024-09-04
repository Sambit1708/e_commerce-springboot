package com.eCommerce.controller;

import java.security.Principal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eCommerce.dto.UserDto;
import com.eCommerce.jwt.JwtRequest;
import com.eCommerce.jwt.JwtResponse;
import com.eCommerce.payload.ApiResponseMessage;
import com.eCommerce.payload.UserResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/auth")
@Tag(name = "Authentication")
public interface AuthenticationControllerAPI {

	@PostMapping("/generate-token")
	@Operation(
		description = "Post endpoint for authentiate User",
		summary = "This api is used for generate token based on valid user",
		requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
			content = {@Content(schema = @Schema(implementation = JwtRequest.class))}	
		),
		responses = { @ApiResponse(description = "Created", responseCode = "201", 
									content = @Content(mediaType = "application/json", 
									schema = @Schema(implementation = UserDto.class))),
					  @ApiResponse(description = "Bad Request", responseCode = "400", 
									content = @Content(mediaType = "application/json", 
									schema = @Schema(implementation = ApiResponseMessage.class)))
					}
	)
	public ResponseEntity<?> generateToken(@RequestBody JwtRequest jwtRequest) throws Exception;
	
	// Return the details for current user.
	@GetMapping("/current-user")
	@Operation(
		description = "Get endpoint for current User",
		summary = "This api is used for current User",
		requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
			content = {@Content(schema = @Schema(implementation = Principal.class))}	
		),
		responses = { @ApiResponse(description = "OK", responseCode = "200", 
									content = @Content(mediaType = "application/json", 
									schema = @Schema(implementation = UserResponse.class))),
					  @ApiResponse(description = "Bad Request", responseCode = "500", 
									content = @Content(mediaType = "application/json", 
									schema = @Schema(implementation = ApiResponseMessage.class)))
					}
	)
	public ResponseEntity<UserResponse> getCurrentUser(Principal principal);
	
	@PostMapping("/validate-token")
	@Operation(
		description = "Get endpoint for validate token",
		summary = "This api is used for validate token",
		requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
			content = {@Content(schema = @Schema(implementation = JwtRequest.class))}	
		),
		responses = { @ApiResponse(description = "OK", responseCode = "200", 
									content = @Content(mediaType = "application/json", 
									schema = @Schema(implementation = ApiResponseMessage.class))),
					  @ApiResponse(description = "Bad Request", responseCode = "400", 
									content = @Content(mediaType = "application/json", 
									schema = @Schema(implementation = ApiResponseMessage.class)))
					}
	)
	public ResponseEntity<ApiResponseMessage> validateToken(@RequestBody JwtResponse jwtToken);
}
