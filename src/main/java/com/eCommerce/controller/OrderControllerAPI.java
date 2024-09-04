package com.eCommerce.controller;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eCommerce.dto.OrderDto;
import com.eCommerce.dto.OrderItemSendDto;
import com.eCommerce.modal.prod.Order;
import com.eCommerce.modal.prod.OrderItems;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;

@CrossOrigin("*")
@RestController
@RequestMapping("/order")
@Tag(name = "Order Controller")
public interface OrderControllerAPI {

	@Operation(
		description = "Post endpoint for adding order",
		summary = "This api is used for updating cart details in database",
		requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
				content = @Content(schema = @Schema(implementation = OrderDto.class))	
		),
		responses = { @ApiResponse(description = "created", responseCode = "201",
									content = @Content(mediaType = "application/json", 
									schema = @Schema(implementation = Order.class))
								  ),
					  @ApiResponse(description = "NOT FOUND", responseCode = "404",
									content = @Content(mediaType = "application/json", 
									schema = @Schema(implementation = Order.class))
								  )
					}
	)
	@PostMapping(path = "/add")
	public ResponseEntity<?> order(@RequestBody OrderDto order, Principal principal) throws MessagingException;

	@Operation(
			description = "Get endpoint for Order",
			summary = "This api is used for get Order from database by Id",
			responses = { @ApiResponse(description = "OK", responseCode = "200"),
						  @ApiResponse(description = "NOT FOUND", responseCode = "404")
						}
	)
	@GetMapping(path = "/fetch")
	public ResponseEntity<Order> getOrderOfId(@RequestParam("orderId") @Parameter(
			description = "Id for Order", required = true, in = ParameterIn.QUERY
		)  String orderId);
	
	@Operation(
			description = "Get endpoint for OrderItems",
			summary = "This api is used for get OrderItems from database by Order Id",
			responses = { @ApiResponse(description = "OK", responseCode = "200"),
						  @ApiResponse(description = "NOT FOUND", responseCode = "404")
						}
	)
	@GetMapping(path = "/get/orderItems")
	public ResponseEntity<List<OrderItems>> getOrderItemsOfOrder(
			@RequestParam("orderId") 
			@Parameter(description = "Id for Order", required = true, in = ParameterIn.QUERY)
			String orderId);
	
	@Operation(
			description = "Get endpoint for all OrderItems of user",
			summary = "This api is used for get OrderItems from database by Current User",
			responses = { @ApiResponse(description = "OK", responseCode = "200"),
						  @ApiResponse(description = "NOT FOUND", responseCode = "404")
						}
	)
	@GetMapping(path = "/get/all")
	public ResponseEntity<List<OrderItemSendDto>> getOrderItemsOfUser(Principal principal);
	
	@Operation(
			description = "Get endpoint for OrderItems by	 Id",
			summary = "This api is used for get OrderItems from database by Order Item Id",
			responses = { @ApiResponse(description = "OK", responseCode = "200"),
						  @ApiResponse(description = "NOT FOUND", responseCode = "404")
						}
	)
	@GetMapping("/item/{orderItemId}")
	public ResponseEntity<OrderItems> getOrderItemById(@PathVariable("orderItemId") String orderItemId);
	
	
	@Operation(
			description = "Get endpoint for total average order",
			summary = "This api is used for get total average order from database",
			responses = { @ApiResponse(description = "OK", responseCode = "200"),
						  @ApiResponse(description = "NOT FOUND", responseCode = "404")
						}
	)
	@GetMapping("/by-totalAvgOrder")
	public ResponseEntity<Map<String, Object>> getTotalAvgOrder();
	
	
	@Operation(
			description = "Get endpoint by year month wise order",
			summary = "This api is used for get year month wise order from database",
			responses = { @ApiResponse(description = "OK", responseCode = "200"),
					@ApiResponse(description = "NOT FOUND", responseCode = "404")
			}
	)
	@GetMapping("/by-yearMonthWiseOrder")
	public ResponseEntity<List<Map<String, Object>>> getYearMonthWiseOrder();
	
	
	@Operation(
			description = "Get endpoint for total brand wise order",
			summary = "This api is used for get total brand wise order from database",
			responses = { @ApiResponse(description = "OK", responseCode = "200"),
					@ApiResponse(description = "NOT FOUND", responseCode = "404")
			}
	)
	@GetMapping("/by-brandWiseOrder")
	public ResponseEntity<List<Map<String, Object>>> getBrandWiseOrder();
	
	@Operation(
			description = "Get endpoint for all orders",
			summary = "This api is used for get all orders from database",
			responses = { @ApiResponse(description = "OK", responseCode = "200"),
					@ApiResponse(description = "NOT FOUND", responseCode = "404")
			}
	)
	@GetMapping("/all")
	public ResponseEntity<List<Order>> getAllOrders();
}
