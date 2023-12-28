package com.example.demo.service;

import com.example.demo.constant.OrderEventEnum;
import com.example.demo.constant.OrderStatusEnum;
import com.example.demo.model.Order;
import com.example.demo.repo.OrderRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

/**
 * User: RuzzZZ
 * Date: 2023/12/27
 * Time: 15:07
 */
@Slf4j
@Service
public class OrderService {


    @Autowired
    private StateMachine<OrderStatusEnum, OrderEventEnum> orderStateMachine;

    @Autowired
    private StateMachinePersister<OrderStatusEnum, OrderEventEnum, String> orderStateMachinePersister;

    @Autowired
    private OrderRepo orderRepo;

    public String createOrder() {
        return orderRepo.createOrder();
    }

    public String trigger(OrderEventEnum event, String orderId) {
        Order order = orderRepo.getOrder(orderId);
        if (order == null) {
            return "order not exist";
        }
        try {
            orderStateMachine.startReactively().subscribe();
            // 设置状态机状态
            orderStateMachinePersister.restore(orderStateMachine, orderId);
            // 发送事件
            Map<String, Object> content = new HashMap<>();
            content.put("order", order);
            Message<OrderEventEnum> message = MessageBuilder.createMessage(event, new MessageHeaders(content));
            orderStateMachine.sendEvent(Mono.just(message)).blockLast();
            // 保存状态机状态
            orderStateMachinePersister.persist(orderStateMachine, orderId);
        } catch (Exception e) {
            log.error("failed to execute state machine,orderId:{}", orderId, e);
            return "failed:" + e.getMessage();
        } finally {
            orderStateMachine.stopReactively();
        }
        return "ok";
    }

    public Order orderInfo(String orderId) {
        return orderRepo.getOrder(orderId);
    }
}
