package com.wb.bench.constant;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wb.bench.annotation.ApiEnum;
import com.wb.bench.annotation.ApiEnumProperty;


/**
 * @Author: songhanlin
 * @Date: Created In 17:36 2020/1/15
 * @Description: TODO
 */
@ApiEnum(dataType = "java.lang.String")
public enum SaasStatus {

    /**
     * 启用
     */
    @ApiEnumProperty("enable：启用")
    ENABLE("enable"),

    /**
     * 禁用
     */
    @ApiEnumProperty("disable：禁用")
    DISABLE("disable");

    SaasStatus(String desc) {
        this.desc = desc;
    }

    /**
     * 描述信息
     */
    private String desc;

    @JsonCreator
    public static SaasStatus fromValue(String value) {
        return valueOf(value);
    }

    @JsonValue
    public String toValue() {
        return desc;
    }
}
