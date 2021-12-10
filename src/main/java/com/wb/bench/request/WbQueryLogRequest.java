package com.wb.bench.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@ApiModel
@AllArgsConstructor
@NoArgsConstructor
public class WbQueryLogRequest implements Serializable {

    /**
     * 订单ID
     */
    @ApiModelProperty(name = "订单ID")
    private String orderId;

    /**
     * 回调地址
     */
    @ApiModelProperty(name = "回调地址")
    private String callBackUrl;

    /**
     * customerId
     */
    @ApiModelProperty(name = "customerId")
    private String customerId;

    /**
     * 创建时间
     */
    @ApiModelProperty(name = "创建时间")
    private LocalDateTime createTime;

}
