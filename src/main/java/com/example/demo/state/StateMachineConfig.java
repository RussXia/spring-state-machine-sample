package com.example.demo.state;

import com.example.demo.constant.OrderEventEnum;
import com.example.demo.constant.OrderStatusEnum;
import com.example.demo.model.Order;
import com.example.demo.repo.OrderRepo;
import com.example.demo.state.action.ErrorAction;
import com.example.demo.state.action.RejectGoodsAction;
import com.example.demo.state.action.SimpleAction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.StateMachineContext;
import org.springframework.statemachine.StateMachinePersist;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.persist.DefaultStateMachinePersister;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.statemachine.support.DefaultStateMachineContext;

import java.util.EnumSet;

/**
 * User: RuzzZZ
 * Date: 2023/12/26
 * Time: 16:58
 */
@Slf4j
@Configuration
@EnableStateMachine(name = "orderStateMachine", contextEvents = false)
public class StateMachineConfig extends StateMachineConfigurerAdapter<OrderStatusEnum, OrderEventEnum> {


    @Autowired
    private SimpleAction simpleAction;

    @Autowired
    private ErrorAction errorAction;

    @Autowired
    private RejectGoodsAction rejectGoodsAction;

    @Autowired
    private OrderRepo orderRepo;

    @Override
    public void configure(StateMachineConfigurationConfigurer<OrderStatusEnum, OrderEventEnum> config)
            throws Exception {
        config
                .withConfiguration()
                .autoStartup(true);
    }

    @Override
    public void configure(StateMachineStateConfigurer<OrderStatusEnum, OrderEventEnum> states)
            throws Exception {
        states.withStates()
                .initial(OrderStatusEnum.UNPAID)
                .states(EnumSet.allOf(OrderStatusEnum.class));
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<OrderStatusEnum, OrderEventEnum> transitions)
            throws Exception {
        transitions
                .withExternal()
                .source(OrderStatusEnum.UNPAID).target(OrderStatusEnum.WAITING_DELIVERY)
                .event(OrderEventEnum.PAY).action(simpleAction, errorAction)
                .and()
                .withExternal()
                .source(OrderStatusEnum.WAITING_DELIVERY).target(OrderStatusEnum.WAITING_FOR_RECEIVE)
                .event(OrderEventEnum.DELIVERY).action(simpleAction, errorAction)
                .and()
                .withExternal()
                .source(OrderStatusEnum.WAITING_FOR_RECEIVE).target(OrderStatusEnum.RECEIVED)
                .event(OrderEventEnum.RECEIVE).action(simpleAction, errorAction)
                .and()
                .withExternal()
                .source(OrderStatusEnum.WAITING_FOR_RECEIVE).target(OrderStatusEnum.REJECTED)
                .event(OrderEventEnum.REJECT_GOODS).action(rejectGoodsAction, errorAction);
    }

    @Bean("orderStateMachinePersister")
    public StateMachinePersister<OrderStatusEnum, OrderEventEnum, String> orderStateMachinePersister() {
        return new DefaultStateMachinePersister<>(new StateMachinePersist<OrderStatusEnum, OrderEventEnum, String>() {
            @Override
            public void write(StateMachineContext<OrderStatusEnum, OrderEventEnum> context, String orderId) {
                // 进行持久化操作
                Order order = orderRepo.updateOrder(orderId, context.getState().getCode());
                if (order == null) {
                    throw new RuntimeException("order not exist!");
                }
            }

            @Override
            public StateMachineContext<OrderStatusEnum, OrderEventEnum> read(String orderId) {
                // 读取状态并设置到context中
                Order order = orderRepo.getOrder(orderId);
                if (order == null) {
                    throw new RuntimeException("order not exist!");
                }
                OrderStatusEnum orderStatusEnum = OrderStatusEnum.getByCode(order.getStatus());
                if (orderStatusEnum == null) {
                    throw new RuntimeException("illegal order status!");
                }
                return new DefaultStateMachineContext<>(orderStatusEnum, null, null, null);
            }
        });
    }

}
