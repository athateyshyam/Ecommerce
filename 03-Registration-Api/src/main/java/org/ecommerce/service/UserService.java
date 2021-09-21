package org.ecommerce.service;

import org.ecommerce.dto.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService{
	public UserDTO registerUser(UserDTO userDto);
	public UserDTO getUserByUsername(String username);
}
