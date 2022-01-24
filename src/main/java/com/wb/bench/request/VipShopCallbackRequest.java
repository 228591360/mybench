package com.wb.bench.request;

import com.wb.bench.VO.VipShopCallbackVO;
import lombok.Data;

import java.io.Serializable;

@Data
public class VipShopCallbackRequest implements Serializable {
    private Integer code;
    private String msg;
    private VipShopCallbackVO data;
}
