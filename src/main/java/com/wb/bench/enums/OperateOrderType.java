package com.wb.bench.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import com.wb.bench.annotation.ApiEnum;
import com.wb.bench.annotation.ApiEnumProperty;


/**
 * 操作订单退单枚举
 * Created by chenyufei on 2017/4/22.
 */
@ApiEnum
public enum OperateOrderType {
    /**
     * 订单
     */
    @ApiEnumProperty("订单")
    ORDER("order"),

    /**
     * 退单
     */
    @ApiEnumProperty("退单")
    RETURN_ORDER("returnOrder");

    private final String value;

    OperateOrderType(String value) {
        this.value = value;
    }

    @JsonValue
    public String toValue() {
        return value;
    }
}
