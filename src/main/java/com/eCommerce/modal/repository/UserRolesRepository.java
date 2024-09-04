package com.eCommerce.modal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eCommerce.modal.UserRoles;
import com.eCommerce.modal.User;
import java.util.Set;


public interface UserRolesRepository extends JpaRepository<UserRoles, String> {

	Set<UserRoles> findByUser(User user);
}
