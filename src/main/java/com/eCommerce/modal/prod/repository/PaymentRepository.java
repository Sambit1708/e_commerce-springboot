package com.eCommerce.modal.prod.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eCommerce.modal.prod.Payment;

public interface PaymentRepository extends JpaRepository<Payment, String> {

}
