package org.ecommerce.repository;

import java.util.Date;
import java.util.List;

import org.ecommerce.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
public OrderEntity findByOrderId(String orderId);
public List<OrderEntity>findByUsernameAndDateBetween(String username,Date fromDate,Date toDate);
}
