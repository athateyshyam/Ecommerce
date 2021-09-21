package org.ecommerce.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.ecommerce.dto.CartDTO;
import org.ecommerce.dto.OrderDTO;
import org.ecommerce.dto.PaymentDTO;
import org.ecommerce.dto.ProductDTO;
import org.ecommerce.repository.OrderRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(OrderAnnotation.class)
public class OrderServiceImplTest {
	@Mock
	private OrderRepository orderRepository;
	@InjectMocks
	private OrderServiceImpl orderServiceImpl;

	static CartDTO cart;
	static ProductDTO productDto;
	static PaymentDTO paymentDto;
	static ConcurrentMap<String, ProductDTO> cartproductList;

	@BeforeAll
	public static void setUp() {
		cart = new CartDTO();
		productDto = new ProductDTO();
		productDto.setProductName("HP-NOTEBOOK");
		productDto.setProductQuantity(5);
		productDto.setProductPrice(40000d);
		productDto.setProductHsn("HP2546");
		productDto.setProductDescription("Hp Laptop");
		cartproductList = new ConcurrentHashMap<>();
		cartproductList.put("HP2546", productDto);
		cart.setUsername("virat@cricket.com");
		cart.setCartList(cartproductList);
		paymentDto=new PaymentDTO();
		paymentDto.setUsername("BNQPA2383E");
		paymentDto.setPassword("QWS_wSoU");
		paymentDto.setFromAccount("1224406");
	}

	@Test
	void testPlaceOrder() {
		when(orderServiceImpl.payBill(paymentDto)).thenReturn(true);
		OrderDTO dto=orderServiceImpl.placeOrder(cart, paymentDto);
		assertEquals("Paid", dto.getPaymentStatus());
	}
}
