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
    @ApiModelProperty(name = "customer_id")
    private String customerId;


    /**
     * 服务id
     */
    @ApiModelProperty(name = "服务id")
    private String serviceId;

    /**
     * 服务名称
     */
    @ApiModelProperty(name = "服务名称")
    private String serviceName;


}
