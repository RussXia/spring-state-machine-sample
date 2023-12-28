package com.example.demo.controller;

import com.example.demo.constant.OrderEventEnum;
import com.example.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * User: RuzzZZ
 * Date: 2023/12/26
 * Time: 17:05
 */
@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/create")
    public String createOrder() {
        return orderService.createOrder();
    }

    @GetMapping("/{event}/{orderId}")
    public String trigger(@PathVariable("event") OrderEventEnum event,
                          @PathVariable("orderId") String orderId) {
        if (event == null) {
            return "not supported event";
        }
        return orderService.trigger(event, orderId);
    }

    @GetMapping("/info/{orderId}")
    public String orderInfo(@PathVariable("orderId") String orderId) {
        return String.valueOf(orderService.orderInfo(orderId));
    }
}
