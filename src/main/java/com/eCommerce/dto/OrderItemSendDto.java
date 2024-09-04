package com.eCommerce.dto;

import lombok.Data;

@Data
public class OrderItemSendDto {
	
	private String orderId;

	private String orderItemId;
	
	private int quantity;
	
	private double price;
	
	private String color;
	
	private String title;
	
	private String imagePath;
	
	private int size;
	
	private String deliveryDate;
	
	private String updateDate;
	
	private String createDate;
}
