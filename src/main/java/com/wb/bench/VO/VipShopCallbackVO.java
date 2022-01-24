package com.wb.bench.VO;

import lombok.Data;

import java.io.Serializable;

@Data
public class VipShopCallbackVO implements Serializable {
    private String orderId;
    private Object data;
}
