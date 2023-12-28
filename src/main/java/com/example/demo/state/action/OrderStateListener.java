package com.example.demo.state.action;

import com.example.demo.model.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.annotation.EventHeader;
import org.springframework.statemachine.annotation.OnTransition;
import org.springframework.statemachine.annotation.WithStateMachine;
import org.springframework.stereotype.Component;

/**
 * User: RuzzZZ
 * Date: 2023/12/28
 * Time: 14:41
 */
@Slf4j
@Component
@WithStateMachine(name = "orderStateMachine")
public class OrderStateListener {

    @OnTransition(source = "UNPAID", target = "WAITING_DELIVERY")
    public void pay(@EventHeader(name = "order") Order order) {
        log.info("支付成功,增加监听log,orderId:{},status:{}", order.getId(), order.getStatus());
    }

    @OnTransition(source = "WAITING_DELIVERY", target = "WAITING_FOR_RECEIVE")
    public void deliver(@EventHeader(name = "order") Order order) {
        log.info("发货成功,增加监听log,orderId:{},status:{}", order.getId(), order.getStatus());
    }

    @OnTransition(source = "WAITING_FOR_RECEIVE", target = "RECEIVED")
    public void receive(@EventHeader(name = "order") Order order) {
        log.info("收货成功,增加监听log,orderId:{},status:{}", order.getId(), order.getStatus());
    }

    @OnTransition(target = "REJECTED")
    public void rejectTransition(@EventHeader(name = "order") Order order) {
        log.info("拒收成功,增加监听log,orderId:{},status:{}", order.getId(), order.getStatus());
    }
}
