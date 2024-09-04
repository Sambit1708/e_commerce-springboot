package com.eCommerce.modal.service;

import java.util.List;
import java.util.Set;

import com.eCommerce.dto.UserDto;
import com.eCommerce.modal.Address;
import com.eCommerce.modal.User;
import com.eCommerce.modal.UserRoles;

public interface UserService {
	
	public UserDto createUserFlow(UserDto userDto);
	
	public UserDto updateUser(UserDto userDto);
	
	public List<UserDto> getUsers();
	
	public User getUser(String email);
	
	public User getUserById(String id);
	
	public void deleteUser(String userId);
	
	public void deleteAllUsers();
	
	public List<Address> getAddressesByUser(String email);
	
	public Address getCurrentAddress(String email);
	
	public Set<UserRoles> getUserRoles(User user); 
}
