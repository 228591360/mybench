package com.wb.bench.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@ApiModel
@AllArgsConstructor
@NoArgsConstructor
public class RechargeRecordResponse implements Serializable {

    private Integer id;

    /**
     * customerId
     */
    @ApiModelProperty(name = "customer_id")
    private String customerId;


    /**
     * 本次充值金额
     */
    @ApiModelProperty(name = "本次充值金额")
    private BigDecimal rechargeAccount;

    /**
     * 充值时间
     */
    @ApiModelProperty(name = "本次充值金额")
    private LocalDateTime rechargeTime;

}
