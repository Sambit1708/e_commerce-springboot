package com.eCommerce.modal.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eCommerce.modal.User;

public interface UserRepository extends JpaRepository<User, String> {

	public Optional<User> findByEmail(String email);
}
