package com.wb.bench.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wb.bench.annotation.ApiEnum;
import com.wb.bench.annotation.ApiEnumProperty;


/**
 * 福利活动发放状态
 */
@ApiEnum
public enum IssueStatus {
    @ApiEnumProperty(" 0：未开始")
    ISSUE_NO_EXEC,

    @ApiEnumProperty(" 1：已结束")
    ISSUE_END_EXEC,

    @ApiEnumProperty(" 2：发放中")
    ISSUE_NOW_EXEC;

    @JsonCreator
    public static IssueStatus fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }

}
