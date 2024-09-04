package com.eCommerce.modal.prod.service;

import java.util.List;
import java.util.Map;

import com.eCommerce.dto.OrderDto;
import com.eCommerce.dto.OrderItemSendDto;
import com.eCommerce.modal.prod.Order;
import com.eCommerce.modal.prod.OrderItems;
import com.eCommerce.modal.prod.OrderStatus;
import com.eCommerce.payload.ApiResponseMessage;

public interface OrderService {

	public Order order(String email, OrderDto orderDto);
	
	public ApiResponseMessage updateOrderStatus(String orderId, OrderStatus orderStatus);
	
	public Order getOrder(String orderId);
	
	public List<Order> getAllOrdersOfUser(String email);
	
	public List<OrderItems> getAllOrderItemsById(String orderId);
	
	public List<OrderItemSendDto> getAllOrderItemsOfOrder(String email);
	
	public OrderItems getOrderItemById(String orderItemId);
	
	public Map<String, Object> getTotalAverageOrder();
	
	public List<Map<String, Object>> getYearAndMonthWise();
	
	public List<Map<String, Object>> getBrandWiseSell();
	
	public List<Order> getAllOrders();
}
