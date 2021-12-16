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
public class CustomerProductRequest implements Serializable {

    /**
     * customerId
     */
    @ApiModelProperty(value = "customerId")
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

}
