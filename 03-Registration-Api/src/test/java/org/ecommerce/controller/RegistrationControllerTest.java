package org.ecommerce.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.security.Principal;

import org.ecommerce.client.CartFeignInterface;
import org.ecommerce.client.OrderFeignClientInterface;
import org.ecommerce.dto.CartDTO;
import org.ecommerce.dto.OrderDTO;
import org.ecommerce.dto.PaymentDTO;
import org.ecommerce.dto.UserDTO;
import org.ecommerce.request.AddressRequest;
import org.ecommerce.request.UserRegistrationRequest;
import org.ecommerce.response.UserRegistrationResponse;
import org.ecommerce.service.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


@ExtendWith(MockitoExtension.class)
@TestMethodOrder(OrderAnnotation.class)
class RegistrationControllerTest {
	@Mock
	private UserService userService;
	@Mock
	private CartFeignInterface cartFeignInterface;
	@Mock
	private OrderFeignClientInterface orderFeignClientInterface;
	@InjectMocks
	private RegistrationController registrationController;

	static ModelMapper mapper;
	static UserRegistrationRequest userRegistrationRequest;
	static UserDTO userDto;
	static AddressRequest addressRequest;
	static Principal principal;
	static CartDTO cartDto;
	static ResponseEntity<CartDTO>cartDtoResponse;
	static ResponseEntity<OrderDTO> orderDtoResponse;
	static PaymentDTO paymentDto;
	static OrderDTO orderDto;

	@BeforeAll
	public static void setUp() {
		addressRequest = new AddressRequest();
		addressRequest.setArea("Chandani Chowk");
		addressRequest.setCity("Dehli");
		addressRequest.setState("Dehli");
		addressRequest.setCountry("India");
		addressRequest.setPin("658475");
		
		userRegistrationRequest = new UserRegistrationRequest();
		userRegistrationRequest.setFirstName("Virat");
		userRegistrationRequest.setLastName("Kohali");
		userRegistrationRequest.setEmail("virat@cricket.com");
		userRegistrationRequest.setMobile("9865741254");
		userRegistrationRequest.setGender("Male");
		userRegistrationRequest.setAge(30);
		userRegistrationRequest.setAddress(addressRequest);
		
		mapper=new ModelMapper();
		userDto=new UserDTO();
		userDto=mapper.map(userRegistrationRequest, UserDTO.class);
		
		principal=new Principal() {
			
			@Override
			public String getName() {
				return "virat@cricket.com";
			}
		};
		
		cartDto=new CartDTO();
		cartDtoResponse=new ResponseEntity<CartDTO>(cartDto, HttpStatus.OK);
		paymentDto=new PaymentDTO();
		paymentDto.setUsername("virat@cricket.com");
		paymentDto.setPassword("QWS_wSoU");
		paymentDto.setFromAccount("1224406");
		orderDto=new OrderDTO();
		orderDtoResponse=new ResponseEntity<OrderDTO>(orderDto, HttpStatus.OK);	
	}

	@Test
	void testRegisterUser() {
		when(userService.registerUser(any(UserDTO.class))).thenReturn(userDto);
		ResponseEntity<UserRegistrationResponse> response=registrationController.registerUser(userRegistrationRequest);
		assertEquals(HttpStatus.OK,response.getStatusCode());
	}

	@Test
	void testGetCart() {
		when(cartFeignInterface.getCart("virat@cricket.com")).thenReturn(cartDtoResponse);
		ResponseEntity<CartDTO>response=registrationController.getCart(principal);
		assertEquals(HttpStatus.OK,response.getStatusCode());
		
	}

	@Test
	void testAddToCart() {
		when(cartFeignInterface.addToCart("DELL-LAPTOP", 7, "virat@cricket.com")).thenReturn(cartDtoResponse);
		ResponseEntity<CartDTO>response=registrationController.addToCart("DELL-LAPTOP", 7, principal);
		assertEquals(HttpStatus.OK,response.getStatusCode());
	}

	@Test
	void testPlaceOrder() {
		when(orderFeignClientInterface.placeOrder(paymentDto)).thenReturn(orderDtoResponse);
		ResponseEntity<OrderDTO>response=registrationController.placeOrder(paymentDto);
		assertEquals(HttpStatus.OK,response.getStatusCode());
	}

}
