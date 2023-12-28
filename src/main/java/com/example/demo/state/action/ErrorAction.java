package com.example.demo.state.action;

import com.example.demo.constant.OrderEventEnum;
import com.example.demo.constant.OrderStatusEnum;
import com.example.demo.model.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

/**
 * User: RuzzZZ
 * Date: 2023/12/27
 * Time: 15:31
 */
@Slf4j
@Component
public class ErrorAction implements Action<OrderStatusEnum, OrderEventEnum> {

    @Override
    public void execute(StateContext<OrderStatusEnum, OrderEventEnum> context) {
        Order order = (Order) context.getMessage().getHeaders().get("order");
        log.error("execute action {} failed,order:{}", context.getEvent().getDesc(), order, context.getException());
        // error order info
        log.info("send order failed message,order:{}", order);
    }
}
