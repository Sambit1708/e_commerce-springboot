package com.eCommerce.dto;

import java.util.List;

import com.eCommerce.modal.Address;
import com.eCommerce.modal.prod.Order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceDto {

	private String customerName;
	
	private String invoiceDate;
	
	private Address address;
	
	private List<InvoiceItemDto> invoiceItemDtos;
	
	private Order order;
}
