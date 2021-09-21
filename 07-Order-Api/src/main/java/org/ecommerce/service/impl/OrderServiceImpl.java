package org.ecommerce.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.ecommerce.dto.AmountTransferRequest;
import org.ecommerce.dto.CartDTO;
import org.ecommerce.dto.OrderDTO;
import org.ecommerce.dto.PaymentDTO;
import org.ecommerce.dto.ProductDTO;
import org.ecommerce.dto.UserBankLoginRequest;
import org.ecommerce.entity.OrderEntity;
import org.ecommerce.exceptions.OrderServiceException;
import org.ecommerce.exceptions.errormessages.ErrorMessages;
import org.ecommerce.repository.OrderRepository;
import org.ecommerce.service.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private RestTemplate restTemplate;

	@Override
	public OrderDTO placeOrder(CartDTO cart,PaymentDTO paymentDto) {
		if(cart.getCartList().isEmpty())
			throw new OrderServiceException(ErrorMessages.CART_IS_EMPTY.getErrorMessage());
		double total = 0;
		for (Map.Entry<String, ProductDTO> entry : cart.getCartList().entrySet()) {
			ProductDTO productDto = entry.getValue();
			total = total + (productDto.getProductQuantity() * productDto.getProductPrice());
		}
		OrderDTO orderDto = new OrderDTO();
		orderDto.setOrderAmount(total);
		orderDto.setUsername(cart.getUsername());
		orderDto.setOrderId(UUID.randomUUID().toString());
		paymentDto.setToAccount("1737288");
		paymentDto.setBalance(total);
	
		if(!payBill(paymentDto))
			throw new OrderServiceException("Payment is failed.");
		orderDto.setPaymentStatus("Paid");
		ModelMapper mapper = new ModelMapper();
		OrderEntity orderEntity = mapper.map(orderDto, OrderEntity.class);
		orderEntity=orderRepository.save(orderEntity);
		orderDto=mapper.map(orderEntity, OrderDTO.class);
		return orderDto;
	}

	
	public boolean payBill(PaymentDTO paymentDto) {
		ModelMapper mapper=new ModelMapper();
		UserBankLoginRequest userRequest=mapper.map(paymentDto, UserBankLoginRequest.class);
		HttpHeaders headers = new HttpHeaders();
	      headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	      HttpEntity<UserBankLoginRequest> entity = new HttpEntity<>(userRequest,headers);
	      String token=restTemplate.exchange(
	    	         "http://localhost:8080/api/login", HttpMethod.POST, entity, String.class).getHeaders().getFirst("Authorization");
	      
	      AmountTransferRequest account=mapper.map(paymentDto, AmountTransferRequest.class);
	      headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	      headers.add("Authorization", token);
	      HttpEntity<AmountTransferRequest> amountRequest = new HttpEntity<>(account,headers);
	     HttpStatus status= restTemplate.exchange(
	    	         "http://localhost:8080/api/transfer-amount", HttpMethod.POST, amountRequest, String.class).getStatusCode();
	     if(status==HttpStatus.OK)
	    	 return true;
		return false;
	}


	@Override
	public OrderDTO getOrderByOrderId(String orderId) {
		OrderEntity orderEntity=orderRepository.findByOrderId(orderId);
		
		return new ModelMapper().map(orderEntity, OrderDTO.class);
	}


	@Override
	public List<OrderDTO> getOrderHistory(String username, String fromDate,String toDate) throws ParseException {
		List<OrderDTO>orderDtoList=null;
		Date dateFrom=new SimpleDateFormat("yyyy-MM-dd").parse(fromDate); 
		Date dateTo=new SimpleDateFormat("yyyy-MM-dd").parse(toDate);
		List<OrderEntity>orderEntityList=orderRepository.findByUsernameAndDateBetween(username, dateFrom,dateTo);
		if(!orderEntityList.isEmpty()) {
			ModelMapper mapper=new ModelMapper();
			orderDtoList=new ArrayList<>();
			for(OrderEntity order:orderEntityList) {
				OrderDTO orderDto=mapper.map(order, OrderDTO.class);
				orderDtoList.add(orderDto);
			}
			return orderDtoList;	
		}
		return null;
	}
	
	
}
