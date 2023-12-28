package com.example.demo.repo;

import com.example.demo.constant.OrderStatusEnum;
import com.example.demo.model.Order;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * User: RuzzZZ
 * Date: 2023/12/27
 * Time: 15:56
 */
@Repository
public class OrderRepo {
    private final Map<String, Order> orderMap = new HashMap<>();

    public String createOrder() {
        Order order = new Order();
        order.setId(UUID.randomUUID().toString());
        order.setStatus(OrderStatusEnum.UNPAID.getCode());
        orderMap.put(order.getId(), order);
        return order.getId();
    }

    public Order getOrder(String orderId) {
        return orderMap.get(orderId);
    }


    public Order updateOrder(String orderId, Integer status) {
        Order order = orderMap.get(orderId);
        if (order != null) {
            order.setStatus(status);
        }
        return order;
    }
}
