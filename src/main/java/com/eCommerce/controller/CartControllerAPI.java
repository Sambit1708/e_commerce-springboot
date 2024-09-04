package com.eCommerce.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eCommerce.dto.CartDto;
import com.eCommerce.modal.prod.Cart;
import com.eCommerce.modal.prod.CartItems;
import com.eCommerce.payload.ApiResponseMessage;

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
@RequestMapping("/cart")
@Tag(name = "Cart")
public interface CartControllerAPI {

	@GetMapping("/getAll")
	@Operation(
		description = "Get endpoint for to get all Cart Items",
		summary = "This api is used for getting all cart details of current user",
		responses = { @ApiResponse(description = "OK", responseCode = "200", 
									content = @Content(mediaType = "application/json", 
									schema = @Schema(implementation = CartItems.class)))
					}
	)
	public ResponseEntity<List<CartItems>> getAllCartItems(Principal principal);
	
	
	@PostMapping(path = "/add_to_cart")
	@Operation(
		description = "POST endpoint for to add cart details",
		summary = "This api is used for adding cart details into database",
		requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
				content = @Content(schema = @Schema(implementation = CartDto.class))	
		),
		responses = { @ApiResponse(description = "created", responseCode = "201",
									content = @Content(mediaType = "application/json", 
									schema = @Schema(implementation = Cart.class)))
					}
	)
	public ResponseEntity<Cart> addToCart(Principal principle, 
											@Valid @RequestBody CartDto cartDtos);
	
	
	@PutMapping(path = "/update_cart")
	@Operation(
		description = "PUT endpoint for to update cart",
		summary = "This api is used for updating cart details in database",
		parameters = {
				@Parameter(
					description = "Cart Id",
					required = true,
					in = ParameterIn.PATH
				)
		},
		requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
				content = @Content(schema = @Schema(implementation = CartDto.class))	
		),
		responses = { @ApiResponse(description = "created", responseCode = "201",
									content = @Content(mediaType = "application/json", 
									schema = @Schema(implementation = Cart.class)))
					}
	)
	public ResponseEntity<Cart> updateCartQuantity(Principal principle, 
													@Valid @RequestBody CartDto cartDto, 
													@RequestParam("cartId") String cartId);
	
	@DeleteMapping(path = "/remove")
	@Operation(
		description = "DELETE endpoint for to delete cart",
		summary = "This api is used for updating cart details in database",
		parameters = {
				@Parameter(
					description = "Cart Item Id", required = true, in = ParameterIn.QUERY
				)
		},
		responses = { @ApiResponse(description = "OK", responseCode = "200",
									content = @Content(mediaType = "application/json", 
									schema = @Schema(implementation = ApiResponseMessage.class)))
					}
	)
	public ResponseEntity<ApiResponseMessage> removeCartItem(
												@RequestParam("cartItemId") String cartItemId);
	@GetMapping(path = "/get")
	@Operation(
			description = "Get endpoint for to get cart",
			summary = "This api is used for updating cart info from database",
			
			responses = { @ApiResponse(description = "OK", responseCode = "200",
							content = @Content(mediaType = "application/json", 
							schema = @Schema(implementation = Cart.class)))
						}
	)
	public ResponseEntity<Cart> getCartDetail(Principal principal);
}
