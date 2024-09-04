package com.eCommerce.controller.impl;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import com.eCommerce.controller.OrderControllerAPI;
import com.eCommerce.dto.OrderDto;
import com.eCommerce.dto.OrderItemSendDto;
import com.eCommerce.modal.prod.Order;
import com.eCommerce.modal.prod.OrderItems;
import com.eCommerce.modal.prod.service.OrderProcessService;
import com.eCommerce.modal.prod.service.OrderService;
import com.eCommerce.payload.ApiResponseMessage;
import com.eCommerce.payload.ECommerceConstant;

import jakarta.mail.MessagingException;

@Controller
public class OrderController implements OrderControllerAPI {

	private final OrderProcessService orderProcessService;
	
	private final OrderService orderService;
	
	public OrderController(OrderProcessService orderProcessService, OrderService orderService) {
		super();
		this.orderProcessService = orderProcessService;
		this.orderService = orderService;
	}

	@Override
	public ResponseEntity<?> order(OrderDto orderDto, Principal principal) throws MessagingException {
		this.orderProcessService.processOrder(principal.getName(), orderDto);
		ApiResponseMessage apiMessage = 
				ApiResponseMessage.builder()
									.status(true)
									.statusCode(ECommerceConstant.CREATED)
									.message("Order has been created.")
									.build();
		return new ResponseEntity<>(apiMessage, HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<Order> getOrderOfId(String orderId) {
		Order order = this.orderService.getOrder(orderId);
		if(order == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(order, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<List<OrderItems>> getOrderItemsOfOrder(String orderId) {
		List<OrderItems> orderItems = 
				this.orderService.getAllOrderItemsById(orderId);
		if(orderItems == null) {
			return new ResponseEntity<>(orderItems, HttpStatus.NOT_FOUND);
		}
		return ResponseEntity.ok(orderItems);
	}

	@Override
	public ResponseEntity<List<OrderItemSendDto>> getOrderItemsOfUser(Principal principal) {
		List<OrderItemSendDto> orderItems = 
				this.orderService.getAllOrderItemsOfOrder(principal.getName());
	
		return ResponseEntity.ok(orderItems);
	}

	@Override
	public ResponseEntity<OrderItems> getOrderItemById(String orderItemId) {
		OrderItems orderItems = this.orderService.getOrderItemById(orderItemId);
		if(orderItems == null) {
			return new ResponseEntity<>(orderItems, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(orderItems, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Map<String, Object>> getTotalAvgOrder() {
		Map<String, Object> result = this.orderService.getTotalAverageOrder();
		if(result.isEmpty()) {
			return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<List<Map<String, Object>>> getYearMonthWiseOrder() {
		List<Map<String, Object>> result = this.orderService.getYearAndMonthWise();
		if(result.isEmpty()) {
			return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<List<Map<String, Object>>> getBrandWiseOrder() {
		List<Map<String, Object>> result = this.orderService.getBrandWiseSell();
		if(result.isEmpty()) {
			return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<List<Order>> getAllOrders() {
		List<Order> orders = this.orderService.getAllOrders();
		if(orders.isEmpty()) {
			return new ResponseEntity<>(orders, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(orders, HttpStatus.OK);
	}
}
