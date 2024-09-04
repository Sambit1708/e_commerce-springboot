package com.eCommerce.modal.service.impl;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eCommerce.dto.AddressDto;
import com.eCommerce.exception.ResourcesNotFoundException;
import com.eCommerce.modal.Address;
import com.eCommerce.modal.User;
import com.eCommerce.modal.repository.AddressRepository;
import com.eCommerce.modal.service.AddressService;

@Service
public class AddressServiceImpl implements AddressService {

	@Autowired
	private AddressRepository addressRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	

	@Override
	public Address createAddress(User user, AddressDto addressDto) {
		Address address = modelMapper.map(addressDto, Address.class);
		LocalDateTime nowDateTime = LocalDateTime.now();
		
		List<Address> addresses = this.getAllAddressesByUser(user);
		addresses.forEach(item -> {
			item.setCurrentAddress(false);
			item.setUpdateDate(LocalDateTime.now());
			this.addressRepository.save(item);
		});
		
		address.setUser(user);
		address.setCurrentAddress(true);
		address.setCreateDate(nowDateTime);
		address.setUpdateDate(nowDateTime);
		address.setPinCode(addressDto.getPinCode());
		
		return this.addressRepository.save(address);
	}

	@Override
	public Address updateAddress(String addressId, AddressDto addressDto) {
		Address loadAddress = this.getAddress(addressId);
		Address newAddress = this.modelMapper.map(addressDto, Address.class);
		
		Class<?> employeeClass = Address.class;
        Field[] fields = employeeClass.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object value = field.get(newAddress);
                if(field.getName() == "pin") {
                	if(value.toString().length() == 6) {
                		field.set(loadAddress, value);
                	}
                }
                else if (value != null) {
                    field.set(loadAddress, value);
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Error updating field " + field.getName(), e);
            }
        }
        loadAddress.setUpdateDate(LocalDateTime.now());
        loadAddress = this.addressRepository.save(loadAddress);
		return loadAddress;
	}

	@Override
	public List<Address> getAllAddressesByUser(User user) {
		return this.addressRepository.findByUser(user);
	}

	@Override
	public void deleteAddress(Address address) {
		this.addressRepository.delete(address);
	}
	
	@Override
	public void deleteAllAddress(User user) {
		this.addressRepository.deleteByUser(user);
	}

	@Override
	public Address getAddress(String addressId) {
		Address address = this.addressRepository.findById(addressId).orElseThrow(
				() -> new ResourcesNotFoundException("Address", "Address Id", addressId)
		);
		return address;
	}

	@Override
	public Address getCurrentAddressByUser(User user) {
		Optional<Address> oAddress = 
				this.addressRepository.findByUserAndCurrentAddress(user, true);
		
		if(oAddress.isEmpty()) {
			Address address = this.addressRepository.findByUser(user).get(0);
			if(address == null) {
				throw new ResourcesNotFoundException("Address", "Current Address", "true");
			}
			return address;
		}
		return oAddress.get();
	}
}
