package com.wb.bench.response;

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
public class CustomerServiceResponse implements Serializable {

    private Integer id;

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

    /**
     * 创建时间
     */
    @ApiModelProperty(name = "创建时间")
    private LocalDateTime createTime;

}
