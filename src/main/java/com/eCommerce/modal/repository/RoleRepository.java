package com.eCommerce.modal.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eCommerce.modal.Role;

public interface RoleRepository extends JpaRepository<Role, String> {

	Optional<Role> findByRoleName(String roleName);
}
