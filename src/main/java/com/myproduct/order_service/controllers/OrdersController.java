package com.myproduct.order_service.controllers;

import com.myproduct.order_service.exceptions.InvalidSkuException;
import com.myproduct.order_service.models.Order;
import com.myproduct.order_service.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class OrdersController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/orders/checkout")
    public ResponseEntity<Object> checkout(@RequestBody Order order){

        System.out.println("ref_number:"+ order);
        try{
            return ResponseEntity.ok(orderService.create(order));
        } catch (InvalidSkuException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("Error: "+ex.getMessage());

        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("Error: "+e.getMessage());
        }

    }

//    @GetMapping("/orders")
//    public ResponseEntity<Iterable<Order>> index(){
//        return ResponseEntity.ok(orderService.getOrders());
//    }

    @GetMapping("/orders/{refNumber}")
    public ResponseEntity<Optional<Order>> show(@PathVariable String refNumber){
        System.out.println("refNumber: "+refNumber);
        return ResponseEntity.ok(orderService.getOrder(refNumber));
    }
}
