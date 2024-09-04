package com.eCommerce.modal.prod.service;

import java.util.List;

import com.eCommerce.dto.OrderItemSendDto;
import com.eCommerce.dto.OrderItemsDto;
import com.eCommerce.modal.prod.Order;
import com.eCommerce.modal.prod.OrderItems;

public interface OrderItemService {

	public List<OrderItems> createOrderItems(Order order, List<OrderItemsDto> orderItemsDto);
	
	public List<OrderItems> getAllOrderItemsOfOrder(Order order);
	
	public List<OrderItemSendDto> getAllOrderItemsOfOrder(String email);
	
	public OrderItems getOrderItemById(String orderItemId);
}
