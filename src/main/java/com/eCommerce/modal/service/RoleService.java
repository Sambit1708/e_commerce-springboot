package com.eCommerce.modal.service;

import java.util.Map;
import java.util.Set;

import com.eCommerce.modal.Role;
import com.eCommerce.modal.User;
import com.eCommerce.modal.UserRoles;

public interface RoleService {
	
	public Role createRole(Map<String, Object> content);
	
	public UserRoles AssignRoleToUser(User user, Role role);
	
	public Role getRoleByName(String roleName);
	
	public Set<UserRoles> getAllRolesOfUser(User user);
}
