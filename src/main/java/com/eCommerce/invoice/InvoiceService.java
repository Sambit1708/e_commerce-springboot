package com.eCommerce.invoice;

import com.eCommerce.dto.InvoiceDto;
import com.eCommerce.modal.prod.Order;

public interface InvoiceService {

	public Invoice generateInvoice(InvoiceDto invoiceDto);
	
	public InvoiceDto prepareInvoice(String email, Order order);

}
