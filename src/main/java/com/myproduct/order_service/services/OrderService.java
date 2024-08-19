package com.myproduct.order_service.services;

import com.myproduct.order_service.dto.AdjustStockResponse;
import com.myproduct.order_service.exceptions.InvalidSkuException;
import com.myproduct.order_service.feign.Product;
import com.myproduct.order_service.models.Order;
import com.myproduct.order_service.models.OrderItem;
import com.myproduct.order_service.repositores.OrderRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private ProductService productService;

    @Transactional
    public Order create(Order order) throws InvalidSkuException, DataIntegrityViolationException {
        orderRepo.save(prepareOrder(order));
        AdjustStockResponse adjustStockResponse =  productService.adjustStock(order);
        if(adjustStockResponse.getErrorCode() != 200) {

            throw new DataIntegrityViolationException("Invalid SKUs"+adjustStockResponse.getStatusMessage());
        }

        return order;
    }

    public Iterable<Order> getOrders(){
        return  orderRepo.findAll();
    }

    public Order prepareOrder(Order order) throws InvalidSkuException {
        Float orderTotal = 0.0f;

        // Use a traditional for loop instead of lambda
        List<OrderItem> orderItems = order.getOrderItems();
        for (OrderItem orderItem : orderItems) {
            try {
                Optional<Product> product = productService.getProduct(orderItem.getSku(), orderItem.getQuantity());
                if (product.isPresent()) {
                    Float price = product.get().getPrice() * orderItem.getQuantity();
                    orderTotal += price; // Equivalent to orderTotal = orderTotal + price;
                    orderItem.setTotalPrice(price);
                    orderItem.setOrder(order);
                } else {
                    throw new InvalidSkuException("Invalid SKU Or SKU is not in Stock: " + orderItem.getSku());
                }
            } catch (Exception e){
                System.out.println("Service Error: "+e.getMessage());
                throw new InvalidSkuException("Invalid SKU Or SKU is not in Stock "+"SKU:" + orderItem.getSku());
            }
        }

        System.out.println("ref_number:"+ order.getRefNumber());
        System.out.println("customer_number:"+ order.getCustomerCode());
        order.setTotalPaidAmount(orderTotal);
        return order;
    }

    public Optional<Order> getOrder(String refNumber) {
        List<Order> orders = orderRepo.findByRefNumber(refNumber);
//        System.out.println("Orders: "+orders);
        return orders.stream().findFirst();
    }
}
