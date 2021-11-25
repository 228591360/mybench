package com.wb.bench.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class RechargeRequest implements Serializable {

    /**
     * 客户id
     */
    @NotBlank
    private String customerId;

    /**
     * 本次充值金额
     */
    @NotNull
    private BigDecimal RechargeAccount;
}
