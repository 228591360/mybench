package com.wb.bench.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wb.bench.annotation.ApiEnum;
import com.wb.bench.annotation.ApiEnumProperty;


/**
 * 性别类型 0女 1男 2保密
 * @author zhangwenchang
 */
@ApiEnum
public enum GenderType {
    @ApiEnumProperty("女")
    FEMALE,
    @ApiEnumProperty("男")
    MALE,
    @ApiEnumProperty("保密")
    SECRET;

    @JsonCreator
    public GenderType fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }
}
