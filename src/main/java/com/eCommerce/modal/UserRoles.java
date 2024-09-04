package com.eCommerce.modal;

import java.time.LocalDateTime;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRoles {

	@Id
	@UuidGenerator
	private String id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private User user;
	
	@ManyToOne
	private Role role;
	
	private LocalDateTime createDate;
	private LocalDateTime updateDate;

	public UserRoles(User user, Role role) {
		super();
		this.user = user;
		this.role = role;
	}
}
