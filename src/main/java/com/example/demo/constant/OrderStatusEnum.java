package com.example.demo.constant;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * User: RuzzZZ
 * Date: 2023/12/26
 * Time: 16:53
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum OrderStatusEnum {

    UNPAID(1, "待支付"),
    WAITING_DELIVERY(2, "待发货"),
    WAITING_FOR_RECEIVE(3, "待收货"),
    RECEIVED(4, "已签收"),
    REJECTED(5, "已拒收"),
    ;
    private final int code;

    private final String desc;

    public static OrderStatusEnum getByCode(int code) {
        for (OrderStatusEnum orderStatusEnum : OrderStatusEnum.values()) {
            if (orderStatusEnum.getCode() == code) {
                return orderStatusEnum;
            }
        }
        return null;
    }

}
