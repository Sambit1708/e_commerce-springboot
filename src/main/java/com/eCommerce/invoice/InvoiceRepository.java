package com.eCommerce.invoice;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface InvoiceRepository extends JpaRepository<Invoice, String> {

	@Query(value = "SELECT * FROM invoice ORDER BY invoice_number DESC LIMIT 1;", nativeQuery = true)
	Optional<Invoice> findTopInvoiceByInvoiceNumberDesc();
}
