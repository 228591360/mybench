package com.wb.bench.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wb.bench.annotation.ApiEnum;
import com.wb.bench.annotation.ApiEnumProperty;


import java.io.Serializable;

/**
 * 0未删除 1已删除
 * Created by zhangjin on 2017/3/22.
 */
@ApiEnum
public enum DeleteFlag implements Serializable {
    @ApiEnumProperty("否")
    NO,
    @ApiEnumProperty("是")
    YES;

    @JsonCreator
    public static DeleteFlag fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }
}
