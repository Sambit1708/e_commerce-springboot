package com.eCommerce.modal;

import org.springframework.security.core.GrantedAuthority;

import lombok.AllArgsConstructor;

@SuppressWarnings("serial")
@AllArgsConstructor
public class Authority implements GrantedAuthority {
	
	private String authority;
	
	@Override
	public String getAuthority() {
		return this.authority;
	}

}
