package com.wb.bench.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@ApiModel
@AllArgsConstructor
@NoArgsConstructor
public class CustomerServiceRequest implements Serializable {

    /**
     * customerId
     */
    @ApiModelProperty(value = "customer_id")
    private String customerId;


    /**
     * 服务id
     */
    @ApiModelProperty(value = "服务id")
    private String serviceId;

    /**
     * 服务名称
     */
    @ApiModelProperty(value = "服务名称")
    private String serviceName;

    /**
     * 服务定价
     */
    @ApiModelProperty(value = "服务定价")
    private String servicePrice;

}
