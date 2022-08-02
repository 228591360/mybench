package com.wb.bench.VO;

import lombok.Data;

import java.io.Serializable;

@Data
public class VipShopBVO implements Serializable {
    private String code;
    private String msg;
    private Object data;
    private String requestId;
}
