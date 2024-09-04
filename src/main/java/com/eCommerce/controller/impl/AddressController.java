package com.eCommerce.controller.impl;

import java.security.Principal;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;

import com.eCommerce.controller.AddressControllerAPI;
import com.eCommerce.dto.AddressDto;
import com.eCommerce.exception.CreationFailedException;
import com.eCommerce.exception.ResourcesNotFoundException;
import com.eCommerce.modal.Address;
import com.eCommerce.modal.User;
import com.eCommerce.modal.service.AddressService;
import com.eCommerce.payload.ApiResponseMessage;
import com.eCommerce.payload.ECommerceConstant;


@Controller
public class AddressController implements AddressControllerAPI {
	
	private AddressService addressService;
	
	private UserDetailsService userDetailsService;
	
	public AddressController(AddressService addressService, UserDetailsService userDetailsService) {
		this.addressService = addressService;
		this.userDetailsService = userDetailsService;
	}
	
	@Override
	public ResponseEntity<Address> createAddress(Principal principal, AddressDto addressDto) {
		User user = (User)this.userDetailsService.loadUserByUsername(principal.getName());
		Address address = this.addressService.createAddress(user, addressDto);
		if(address == null) {
			throw new CreationFailedException("Address", "address", addressDto.getAddress());
		}
		return new ResponseEntity<>(address, HttpStatus.CREATED);
	}
	
	@Override
	public ResponseEntity<Address> updateAddress(AddressDto addressDto, String addrId) throws Exception {
		Address address = this.addressService.updateAddress(addrId, addressDto);
		return new ResponseEntity<>(address, HttpStatus.CREATED);
	}
	
	@Override
	public ResponseEntity<?> deleteAddressOfUser(String addrId) {
		Address address = this.addressService.getAddress(addrId);
		this.addressService.deleteAddress(address);
		ApiResponseMessage apiMessage = ApiResponseMessage
													.builder()
													.status(true)
													.message(ECommerceConstant.DELETE_ADDRESS_MSG)
													.statusCode(ECommerceConstant.OK)
													.build();
		return new ResponseEntity<>(apiMessage, HttpStatus.OK);
	}
}