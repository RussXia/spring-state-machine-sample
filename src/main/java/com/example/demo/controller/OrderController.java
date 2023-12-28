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

    @GetMapping("/pay/{orderId}")
    public String pay(@PathVariable("orderId") String orderId) {
        return orderService.trigger(OrderEventEnum.PAY, orderId);
    }

    @GetMapping("/delivery/{orderId}")
    public String delivery(@PathVariable("orderId") String orderId) {
        return orderService.trigger(OrderEventEnum.DELIVERY, orderId);
    }

    @GetMapping("/receive/{orderId}")
    public String receive(@PathVariable("orderId") String orderId) {
        return orderService.trigger(OrderEventEnum.RECEIVE, orderId);
    }

    @GetMapping("/reject/{orderId}")
    public String reject(@PathVariable("orderId") String orderId) {
        return orderService.trigger(OrderEventEnum.REJECT, orderId);
    }


    @GetMapping("/info/{orderId}")
    public String orderInfo(@PathVariable("orderId") String orderId) {
        return String.valueOf(orderService.orderInfo(orderId));
    }
}
