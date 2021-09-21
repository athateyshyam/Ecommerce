package org.ecommerce.service.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.ecommerce.dto.AddressDTO;
import org.ecommerce.dto.UserDTO;
import org.ecommerce.entity.UserEntity;
import org.ecommerce.exceptions.UserServiceException;
import org.ecommerce.repository.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(OrderAnnotation.class)
class UserServiceImplTest {
	@Mock
	private UserRepository userRepository;
	@Mock
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@InjectMocks
	private UserServiceImpl userServiceImpl;
	
	static ModelMapper mapper;
	static UserDTO userDto;
	static AddressDTO addressDto;
	static UserEntity userEntity;
	@BeforeAll
	static void setUp() {
		addressDto=new AddressDTO();
		addressDto.setArea("Central");
		addressDto.setCity("Moscow");
		addressDto.setState("States of Russia");
		addressDto.setCountry("Russia");
		addressDto.setPin("784587");
		userDto=new UserDTO();
		userDto.setFirstName("Alexander");
		userDto.setLastName("Alekhine");
		userDto.setEmail("alexander@chess.com");
		userDto.setMobile("6584587985");
		userDto.setGender("Male");
		userDto.setAge(55);
		userDto.setAddress(addressDto);
		
		mapper=new ModelMapper();
		userEntity=new UserEntity();
		userEntity=mapper.map(userDto, UserEntity.class);
	}

	@Test
	@DisplayName("Save UserDetails")
	@Order(1)
	void testRegisterUser() {
		when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);
		when(bCryptPasswordEncoder.encode(any(String.class))).thenReturn("sdfg!@#$%^&*hjkl2345)RTsdfghjklrtyuio$%^&*890UIO(*&^%6098");
		UserDTO dto=userServiceImpl.registerUser(userDto);
		assertNotNull(dto);
	}

	@Test
	@DisplayName("Positive:Get User by username")
	@Order(2)
	void testGetUserByUsername_Positive_Approach() {
		when(userRepository.findByUsername("alexander@chess.com")).thenReturn(userEntity);
		UserDTO dto=userServiceImpl.getUserByUsername("alexander@chess.com");
		assertNotNull(dto);
	}
	
	@Test
	@DisplayName("Negative:Get User by username")
	@Order(3)
	void testGetUserByUsername_Negative_Approach() {
		when(userRepository.findByUsername("alex@chess.com")).thenReturn(null);
		assertThrows(UserServiceException.class, () ->userServiceImpl.getUserByUsername("alex@chess.com"));
	}


}
