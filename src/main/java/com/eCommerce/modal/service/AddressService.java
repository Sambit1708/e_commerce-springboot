package com.eCommerce.modal.service;

import java.util.List;

import com.eCommerce.dto.AddressDto;
import com.eCommerce.modal.Address;
import com.eCommerce.modal.User;

public interface AddressService {

	public Address createAddress(User user, AddressDto addressDto);
	
	public Address updateAddress(String addressId, AddressDto addressDto);
	
	public List<Address> getAllAddressesByUser(User user);
	
	public void deleteAddress(Address address);
	
	public Address getAddress(String addressId);
	
	public void deleteAllAddress(User user);
	
	public Address getCurrentAddressByUser(User user);
}
