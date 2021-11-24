package com.wb.bench.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wb.bench.annotation.ApiEnum;
import com.wb.bench.annotation.ApiEnumProperty;

/**
 * 记录状态 0未处理 1已处理 2暂不处理
 * @author zhangwenchang
 */
@ApiEnum
public enum RecordStatus {
    @ApiEnumProperty("未处理")
    UN_DISPOSED,
    @ApiEnumProperty("已处理")
    PROCESSED,
    @ApiEnumProperty("暂不处理")
    NO_PROCESSED;

    @JsonCreator
    public RecordStatus fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }
}
