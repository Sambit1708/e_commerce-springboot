package com.eCommerce.modal.service.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.eCommerce.modal.User;
import com.eCommerce.modal.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Optional<User> user = this.userRepository.findByEmail(username);
		if(user.isEmpty()) {
			logger.error("User is not present");
		}
		return (user.isEmpty()) ? null : user.get();
	}
}
