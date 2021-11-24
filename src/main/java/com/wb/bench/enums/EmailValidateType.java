package com.wb.bench.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wb.bench.annotation.ApiEnum;
import com.wb.bench.annotation.ApiEnumProperty;


/**
 * 0注册 1忘记密码 2修改密码 3修改支付密码
 */
@ApiEnum
public enum EmailValidateType {

    @ApiEnumProperty("注册")
    REGISTER,
    @ApiEnumProperty("忘记密码")
    FORGOT_PS,
    @ApiEnumProperty("修改密码")
    UPDATE_PS,
    @ApiEnumProperty("修改支付密码")
    UPDATE_PAY_PS;

    @JsonCreator
    public static EmailValidateType fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }
}
