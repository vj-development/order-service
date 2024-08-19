package com.myproduct.order_service.repositores;

import com.myproduct.order_service.models.Order;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepo extends CrudRepository<Order, Integer> {


//    @Query("select o from Orders o where o.refNumber = :refNumber")
    List<Order> findByRefNumber(String refNumber);

}
