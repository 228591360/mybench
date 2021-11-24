package com.wb.bench.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wb.bench.annotation.ApiEnum;
import com.wb.bench.annotation.ApiEnumProperty;


/**
 * <p>发票类型 0普通发票 1增值税专用发票 2增值税电子普通发票</p>
 *
 * @author chenhao
 * @date 2020/5/7
 */
@ApiEnum
public enum InvoiceType {

    @ApiEnumProperty("普通发票")
    NORMAL,

    @ApiEnumProperty("增值税专用发票")
    SPECIAL,

    @ApiEnumProperty("增值税电子普通发票")
    VAT_ELEC_INVOICE;

    @JsonCreator
    public InvoiceType fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }
}
