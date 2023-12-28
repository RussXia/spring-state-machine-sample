package com.example.demo.constant;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * User: RuzzZZ
 * Date: 2023/12/26
 * Time: 16:54
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum OrderEventEnum {

    PAY(1, "支付"),
    DELIVERY(2, "发货"),
    RECEIVE(3, "收货"),
    REJECT_GOODS(4, "拒收"),
    ;
    private final int code;

    private final String desc;
}
