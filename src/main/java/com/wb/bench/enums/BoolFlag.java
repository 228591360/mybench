package com.wb.bench.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wb.bench.annotation.ApiEnum;
import com.wb.bench.annotation.ApiEnumProperty;
import org.springframework.util.StringUtils;

/**
 * 布布型枚举,0否1是
 * Created by daiyitian on 2017/3/22.
 */
@ApiEnum
public enum BoolFlag {

    @ApiEnumProperty("否")
    NO,
    @ApiEnumProperty("是")
    YES;
    @JsonCreator
    public BoolFlag fromValue(int value) {
        return values()[value];
    }

    @JsonCreator
    public static BoolFlag fromValue(String value) {
        if(StringUtils.isEmpty(value)){
            return null;
        }
        return values()[Integer.valueOf(value)];
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }
}
