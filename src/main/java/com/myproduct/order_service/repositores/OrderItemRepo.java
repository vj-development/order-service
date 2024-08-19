package com.myproduct.order_service.repositores;

import com.myproduct.order_service.models.OrderItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepo extends CrudRepository<OrderItem, Integer> {
}
