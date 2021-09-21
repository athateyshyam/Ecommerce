package org.ecommerce.service;

import java.text.ParseException;
import java.util.List;

import org.ecommerce.dto.CartDTO;
import org.ecommerce.dto.OrderDTO;
import org.ecommerce.dto.PaymentDTO;

public interface OrderService {
	public OrderDTO placeOrder(CartDTO cart,PaymentDTO paymentDto);
	public OrderDTO getOrderByOrderId(String orderId);
	public List<OrderDTO> getOrderHistory(String username, String fromDate,String toDate)throws ParseException;
}
