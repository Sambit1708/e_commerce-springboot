package com.eCommerce.modal.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.eCommerce.dto.UserDto;
import com.eCommerce.exception.ResourcesAlreadyExistException;
import com.eCommerce.exception.ResourcesNotFoundException;
import com.eCommerce.modal.Address;
import com.eCommerce.modal.Role;
import com.eCommerce.modal.User;
import com.eCommerce.modal.UserRoles;
import com.eCommerce.modal.prod.service.CartService;
import com.eCommerce.modal.repository.UserRepository;
import com.eCommerce.modal.service.AddressService;
import com.eCommerce.modal.service.RoleService;
import com.eCommerce.modal.service.UserService;
import com.eCommerce.payload.ECommerceConstant;


@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private AddressService addressService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private CartService cartService;
	
	@Override
	public UserDto createUserFlow(UserDto userDto) {
		User user = this.createUser(userDto);
		userDto = this.modelMapper.map(user, UserDto.class);
		
		Role role = this.roleService.getRoleByName(ECommerceConstant.NORMAL_ROLE);
		if(role == null) {
			Map<String, Object> map = new HashMap<>();
			map.put("roleName", ECommerceConstant.NORMAL_ROLE);
			map.put("description", "This role is for normal user");
			role = this.roleService.createRole(map);
			
		}
		this.roleService.AssignRoleToUser(user, role);

		this.cartService.createCartOfUser(user);
		
		return userDto;
	}
	
	private User createUser(UserDto userDto) {
		Optional<User> repoUser = this.userRepository.findByEmail(userDto.getEmail());
		if(repoUser.isPresent()) {
			throw new ResourcesAlreadyExistException("User", "Email", userDto.getEmail());
		}

		User local = this.modelMapper.map(userDto, User.class);
		LocalDateTime nowDateTime = LocalDateTime.now();
		local.setCreateDate(nowDateTime);
		local.setUpdateDate(nowDateTime);
		local.setPassword(this.passwordEncoder.encode(local.getPassword()));
		
		local = this.userRepository.save(local);
		
		return local;
	}

	@Override
	public UserDto updateUser(UserDto userDto) {
		Optional<User> userOp = this.userRepository.findByEmail(userDto.getEmail());
		User user = userOp.orElseThrow(() -> new ResourcesNotFoundException("User", "email", "id"));
		
		user.setFirstName(userDto.getFirstName() != null ? userDto.getFirstName() : user.getFirstName());
		user.setLastName(userDto.getLastName() != null ? userDto.getLastName() : user.getLastName());
		user.setGender(userDto.getGender() != null ? userDto.getGender() : user.getGender());
		user.setPhone(userDto.getPhone() != null ? userDto.getPhone() : user.getPhone());
		user.setUpdateDate(LocalDateTime.now());
		
		user = this.userRepository.save(user);
		userDto = this.modelMapper.map(user, UserDto.class);
		
		return userDto;
	}

	@Override
	public List<UserDto> getUsers() {
		List<User> users = this.userRepository.findAll();
		List<UserDto> userDtos = new ArrayList<>();
		users.forEach((user) -> {
			UserDto userDto = this.modelMapper.map(user, UserDto.class);
			userDtos.add(userDto);
		});
		return userDtos;
	}

	@Override
	public User getUser(String email) {
		return this.userRepository.findByEmail(email)
				.orElseThrow(() -> new ResourcesNotFoundException("User", "Email", email));
	}

	@Override
	public void deleteUser(String userId) {
		this.userRepository.deleteById(userId);
	}

	@Override
	public void deleteAllUsers() {
		this.userRepository.deleteAll();
	}

	@Override
	public User getUserById(String id) {
		User user = this.userRepository.findById(id).orElseThrow(() -> 
					new ResourcesNotFoundException("User", "user id", id));
		return user;
	}

	@Override
	public List<Address> getAddressesByUser(String email) {
		User user = this.getUser(email);
		List<Address> addresses = this.addressService.getAllAddressesByUser(user);
		return addresses;
	}
	
	@Override
	public Address getCurrentAddress(String email) {
		User user = this.getUser(email);
		Address address = this.addressService.getCurrentAddressByUser(user);
		return address;
	}

	@Override
	public Set<UserRoles> getUserRoles(User user) {
		return this.roleService.getAllRolesOfUser(user);
	}

}
