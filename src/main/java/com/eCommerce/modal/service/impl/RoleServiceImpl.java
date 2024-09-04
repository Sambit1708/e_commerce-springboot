package com.eCommerce.modal.service.impl;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eCommerce.modal.Role;
import com.eCommerce.modal.User;
import com.eCommerce.modal.UserRoles;
import com.eCommerce.modal.repository.RoleRepository;
import com.eCommerce.modal.repository.UserRolesRepository;
import com.eCommerce.modal.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private UserRolesRepository userRolesRepository;
	
	@Override
	public Role createRole(Map<String, Object> content) {
		String roleName = null;
		String description = null;
		LocalDateTime nowDateTime = LocalDateTime.now();
		if(content.containsKey("roleName")) {
			roleName = content.get("roleName").toString();
		}
		if(content.containsKey("description")) {
			description = content.get("description").toString();
		}
		
		Role role = new Role();
		role.setRoleName(roleName);
		role.setDescription(description);
		role.setCreateDate(nowDateTime);
		role.setUpdateDate(nowDateTime);

		if(roleName != null || roleName != "") {
			role = this.roleRepository.save(role);
		}
		return role;
	}

	@Override
	public UserRoles AssignRoleToUser(User user, Role role) {
		LocalDateTime nowDateTime = LocalDateTime.now();
		
		UserRoles userRoles = new UserRoles();
		userRoles.setRole(role);
		userRoles.setUser(user);
		userRoles.setCreateDate(nowDateTime);
		userRoles.setUpdateDate(nowDateTime);
		
		if(user != null) {
			userRoles = this.userRolesRepository.save(userRoles);
		}
		return userRoles;
	}

	@Override
	public Role getRoleByName(String roleName) {
		Optional<Role> role = this.roleRepository.findByRoleName(roleName);
		if(role.isEmpty()) {
			return null;
		}
		return role.get();
	}

	
	@Override
	public Set<UserRoles> getAllRolesOfUser(User user) {
		Set<UserRoles> userRoles = this.userRolesRepository.findByUser(user);
		return userRoles;
	}
}
