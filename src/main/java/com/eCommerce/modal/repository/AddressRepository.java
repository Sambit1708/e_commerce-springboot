package com.eCommerce.modal.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eCommerce.modal.Address;
import com.eCommerce.modal.User;

public interface AddressRepository extends JpaRepository<Address, String> {

	public List<Address> findByUser(User user);
	
	public void deleteByUser(User user);
	
	Optional<Address> findByUserAndCurrentAddress(User user, boolean currentAddress);
}
