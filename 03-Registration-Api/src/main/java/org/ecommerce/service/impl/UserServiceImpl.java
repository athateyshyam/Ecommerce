package org.ecommerce.service.impl;

import java.util.ArrayList;
import java.util.UUID;

import javax.transaction.Transactional;

import org.ecommerce.dto.UserDTO;
import org.ecommerce.entity.UserEntity;
import org.ecommerce.exceptions.UserServiceException;
import org.ecommerce.exceptions.errormessages.ErrorMessages;
import org.ecommerce.repository.UserRepository;
import org.ecommerce.service.UserService;
import org.ecommerce.utility.UserUtility;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public UserDTO registerUser(UserDTO userDto) {
		if(userRepository.findByEmail(userDto.getEmail())!=null)
			throw new UserServiceException(ErrorMessages.EMAIL_ALREADY_EXISTS.getErrorMessage());
		userDto.setUserId(UUID.randomUUID().toString());
		ModelMapper mapper = new ModelMapper();
		UserEntity user = mapper.map(userDto, UserEntity.class);
		user.setUsername(userDto.getEmail());
		String password = UserUtility.generatePassword(5);
		user.setEncryptedPassword(bCryptPasswordEncoder.encode(password));
		user = userRepository.save(user);
		userDto = mapper.map(user, UserDTO.class);
		userDto.setPassword(password);
		return userDto;
	}

	@Override
	public UserDTO getUserByUsername(String username) {
		UserEntity userEntity = userRepository.findByUsername(username);
		if (userEntity == null)
			throw new UserServiceException(ErrorMessages.USERNAME_NOT_FOUND.getErrorMessage());
		return new ModelMapper().map(userEntity, UserDTO.class);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity userEntity = userRepository.findByUsername(username);
		if (userEntity == null)
			throw new UsernameNotFoundException(ErrorMessages.USERNAME_NOT_FOUND.getErrorMessage());
		return new User(userEntity.getUsername(), userEntity.getEncryptedPassword(), new ArrayList<>());
	}

}
