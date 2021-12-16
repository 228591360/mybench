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
public class CustomerProductResponse implements Serializable {

    private Integer id;

    /**
     * customerId
     */
    @ApiModelProperty(value = "customer_id")
    private String customerId;


    /**
     * 产品id
     */
    @ApiModelProperty(value = "产品id")
    private String productId;

    /**
     * 产品名称
     */
    @ApiModelProperty(value = "产品名称")
    private String productName;

    /**
     * 产品定价
     */
    @ApiModelProperty(value = "产品定价")
    private String productPrice;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

}
