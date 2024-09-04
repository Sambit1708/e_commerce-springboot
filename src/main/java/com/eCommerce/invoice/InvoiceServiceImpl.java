package com.eCommerce.invoice;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eCommerce.dto.InvoiceDto;
import com.eCommerce.dto.InvoiceItemDto;
import com.eCommerce.modal.prod.Order;
import com.eCommerce.modal.prod.OrderItems;
import com.eCommerce.modal.prod.repository.OrderRepository;

@Service
public class InvoiceServiceImpl implements InvoiceService {

	@Autowired
	private InvoiceRepository invoiceRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private InvoiceItemRepository invoiceItemRepository;
	
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	
	@Override
	public Invoice generateInvoice(InvoiceDto invoiceDto) {
		Invoice invoice = new Invoice();
		invoice.setCustomerName(invoiceDto.getCustomerName());
		invoice.setInvoiceDate(invoiceDto.getInvoiceDate());
		invoice.setAddress(invoiceDto.getAddress());
		invoice.setInvoiceNumber(1);
		
		Optional<Invoice> loadInvoice = 
				this.invoiceRepository.findTopInvoiceByInvoiceNumberDesc();
		if(loadInvoice.isPresent()) {
			invoice.setInvoiceNumber(loadInvoice.get().getInvoiceNumber()+1);
		}
		
		invoice = this.invoiceRepository.save(invoice);
		List<InvoiceItems> invoiceItems = 
				this.saveInvoiceItem(invoice, invoiceDto.getInvoiceItemDtos());
		
		Order order = this.orderRepository.findById(invoiceDto.getOrder().getId()).orElse(null);
		order.setInvoice(invoice);
		order.setUpdateDate(LocalDateTime.now());
		
		order = this.orderRepository.save(order);
		
		invoice.setTotal(String.valueOf(invoiceDto.getOrder().getTotalAmount()));
		invoice.setInvoiceItems(invoiceItems);
		
		return invoice;
	}
	
	private List<InvoiceItems> saveInvoiceItem(Invoice invoice, List<InvoiceItemDto> invoiceItemDtos) {
		List<InvoiceItems> invoiceItems = new ArrayList<>();
		
		for(InvoiceItemDto invoiceItemDto : invoiceItemDtos) {
			InvoiceItems invoiceItem = new InvoiceItems();
			invoiceItem.setDeliveryCharge(invoiceItemDto.getDeliveryCharge());
			invoiceItem.setDescription(invoiceItemDto.getDescription());
			invoiceItem.setQuantity(invoiceItemDto.getQuantity());
			invoiceItem.setUnitPrice(invoiceItemDto.getUnitPrice());
			invoiceItem.setInvoice(invoice);
			
			this.invoiceItemRepository.save(invoiceItem);
			invoiceItems.add(invoiceItem);
		}
		return invoiceItems;
		
	}

	@Override
	public InvoiceDto prepareInvoice(String email, Order order)  {
		List<OrderItems> orderItems = order.getOrderItems();
		InvoiceDto invoiceDto = new InvoiceDto();
		invoiceDto.setCustomerName(order.getAddress().getDeliverTo());
		invoiceDto.setInvoiceDate(formatter.format(LocalDateTime.now()));
		invoiceDto.setAddress(order.getAddress());
		
		List<InvoiceItemDto> invoiceItemDtos = new ArrayList<>();
		
		for(OrderItems orderItem : orderItems) {
			InvoiceItemDto invoiceItemDto = new InvoiceItemDto();
			invoiceItemDto.setDeliveryCharge(50);
			invoiceItemDto.setDescription(orderItem.getProduct().getTitle());
			invoiceItemDto.setQuantity(orderItem.getQuantity());
			invoiceItemDto.setUnitPrice(orderItem.getProduct().getPrice());
			invoiceItemDtos.add(invoiceItemDto);
		}

		invoiceDto.setInvoiceItemDtos(invoiceItemDtos);
		invoiceDto.setOrder(order);
		return invoiceDto;
	}

}
