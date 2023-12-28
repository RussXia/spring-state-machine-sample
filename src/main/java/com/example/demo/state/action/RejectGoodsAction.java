package com.example.demo.state.action;

import com.example.demo.constant.OrderEventEnum;
import com.example.demo.constant.OrderStatusEnum;
import com.example.demo.model.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateContext;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * User: RuzzZZ
 * Date: 2023/12/27
 * Time: 15:35
 */
@Slf4j
@Component
public class RejectGoodsAction extends AbstractBaseAction {

    @Override
    public void execute(StateContext<OrderStatusEnum, OrderEventEnum> stateContext) {
        super.execute(stateContext);
        var order = (Order) stateContext.getMessage().getHeaders().get("order");
        Assert.notNull(order, "order can not be null");
        log.info("send reject message,orderId:{}", order.getId());
        // mock
        //throw new RuntimeException("reject goods failed");
    }
}
