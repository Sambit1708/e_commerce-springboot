package com.eCommerce.modal;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role {

	@Id
	@UuidGenerator
	private String id;
	
	@Column(unique = true)
	@NotBlank(message = "Role name can not be blank.")
	private String roleName;
	
	@NotBlank(message = "Role description can not be blank")
	private String description;
	
	private LocalDateTime createDate;
	private LocalDateTime updateDate;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "role")
	private Set<UserRoles> userRoles = new HashSet<>();

	public Role(@NotBlank(message = "Role name can not be blank.") String roleName,
			@NotBlank(message = "Role description can not be blank") String description) {
		super();
		this.roleName = roleName;
		this.description = description;
	}
}
