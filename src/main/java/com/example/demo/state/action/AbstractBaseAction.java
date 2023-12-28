package com.example.demo.state.action;

import com.example.demo.constant.OrderEventEnum;
import com.example.demo.constant.OrderStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;

/**
 * User: RuzzZZ
 * Date: 2023/12/27
 * Time: 15:25
 */
@Slf4j
public abstract class AbstractBaseAction implements Action<OrderStatusEnum, OrderEventEnum> {

    @Override
    public void execute(StateContext<OrderStatusEnum, OrderEventEnum> stateContext) {
        log.info("execute action {},state message:{}", stateContext.getEvent().getDesc(), stateContext.getMessage());
    }
}
