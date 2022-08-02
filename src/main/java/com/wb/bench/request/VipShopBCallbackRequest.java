package com.wb.bench.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class VipShopBCallbackRequest implements Serializable {
    private Integer code;
    private String msg;
    private Object data;
}
