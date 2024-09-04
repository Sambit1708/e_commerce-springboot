package com.eCommerce.modal.prod.service.impl;

import java.util.ArrayList;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eCommerce.dto.OrderItemSendDto;
import com.eCommerce.dto.OrderItemsDto;
import com.eCommerce.exception.ResourcesNotFoundException;
import com.eCommerce.modal.prod.Order;
import com.eCommerce.modal.prod.OrderItems;
import com.eCommerce.modal.prod.Product;
import com.eCommerce.modal.prod.ProductSize;
import com.eCommerce.modal.prod.repository.OrderItemRepository;
import com.eCommerce.modal.prod.service.OrderItemService;
import com.eCommerce.modal.prod.service.ProductService;
import com.eCommerce.modal.prod.service.ProductSizeService;

@Service
public class OrderItemServiceImpl implements OrderItemService {
	
	@Autowired
	private OrderItemRepository orderItemRepository;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private ProductSizeService productSizeService;
	
	
	@Override
	public List<OrderItems> createOrderItems(Order order, List<OrderItemsDto> orderItemsDtos)
	{
		List<OrderItems> orderItems = new ArrayList<>();
		
		for(int i=0; i<orderItemsDtos.size(); i++) {
			
			Product product = this.productService
									.getProduct(orderItemsDtos.get(i).getProductId());
			ProductSize productSize = this.productSizeService
									.getProductSize(orderItemsDtos.get(i).getProductSizeId());
			LocalDateTime nowDate = LocalDateTime.now();
			
			OrderItems orderItem = new OrderItems();
			orderItem.setProduct(product);
			orderItem.setProductSize(productSize);
			orderItem.setOrder(order);
			orderItem.setPrice(orderItemsDtos.get(i).getPrice());
			orderItem.setQuantity(orderItemsDtos.get(i).getQuantity());
			orderItem.setCreateDate(nowDate);
			orderItem.setUpdateDate(nowDate);
			
			orderItem = this.orderItemRepository.save(orderItem);
			orderItems.add(orderItem);
		}
		return orderItems;
	}

	
	@Override
	public List<OrderItems> getAllOrderItemsOfOrder(Order order) {
		List<OrderItems> orderItems = this.orderItemRepository.findByOrder(order);
		return orderItems;
	}

	@Override
	public List<OrderItemSendDto> getAllOrderItemsOfOrder(String email) {
		List<Map<String, Object>> orderItemsMap = 
				this.orderItemRepository.findOrderItemByUser(email);
		
		List<OrderItemSendDto> orderItems = new ArrayList<>();
		orderItemsMap.forEach((item)->{
			OrderItemSendDto orderItem = new OrderItemSendDto();
			orderItem.setOrderItemId(item.get("id").toString());
			orderItem.setPrice((Double)item.get("price"));
			orderItem.setQuantity((Integer)item.get("quantity"));
			
			orderItem.setCreateDate(item.get("create_date").toString());
			orderItem.setUpdateDate(item.get("update_date").toString());
			
			Product product = this.productService.getProduct(item.get("product_id").toString());
			orderItem.setColor(product.getProductDetails().getColor());
			orderItem.setImagePath(product.getImagePath());
			orderItem.setTitle(product.getTitle());
			
			ProductSize productSize = 
					this.productSizeService.getProductSize(item.get("product_size_id").toString());
			orderItem.setSize(productSize.getSize());
			
			orderItem.setOrderId(item.get("order_id").toString());
			orderItem.setDeliveryDate(item.get("delivery_date").toString());
			
			orderItems.add(orderItem);
		});
		return orderItems;
	}


	@Override
	public OrderItems getOrderItemById(String orderItemId) {
		OrderItems orderItems = this.orderItemRepository.findById(orderItemId)
			.orElseThrow(() -> new ResourcesNotFoundException("Order Items", "Id", orderItemId));
		return orderItems;
	}
}
