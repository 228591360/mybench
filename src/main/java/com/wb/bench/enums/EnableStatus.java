package com.wb.bench.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wb.bench.annotation.ApiEnum;
import com.wb.bench.annotation.ApiEnumProperty;


/**
 * 0未启用 1已启用
 * Created by yinxianzhi on 2019/4/8.
 */
@ApiEnum
public enum EnableStatus {
    @ApiEnumProperty("未启用")
    DISABLE("停用"),
    @ApiEnumProperty("已启用")
    ENABLE("启用");

    private String desc;

    EnableStatus(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    @JsonCreator
    public static EnableStatus fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }
}
