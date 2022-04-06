package com.wb.bench.request;

import com.wb.bench.entity.PageEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class LogRequest extends PageEntity implements Serializable  {
    /**
     * id
     */
    @ApiModelProperty(value = "id")
    private Integer id;

    /**
     * 产品id
     */
    @ApiModelProperty(name = "productId")
    private String productId;

    /**
     * 产品名称
     */
    @ApiModelProperty(value = "产品名称")
    private String productName;

    /**
     * 车架
     */
    @ApiModelProperty(value = "车架")
    private String vin;

    /**
     * 订单ID
     */
    @ApiModelProperty(value = "订单ID")
    private String orderId;

    /**
     * 回调地址
     */
    @ApiModelProperty(value = "回调地址")
    private String callBackUrl;

    /**
     * customerId
     */
    @ApiModelProperty(value = "customerId")
    private String customerId;

    /**
     * 客户名称
     */
    @ApiModelProperty(value = "客户名称")
    private String customerName;

    /**
     * 收费
     */
    @ApiModelProperty(value = "收费")
    private String toll;


    /**
     * 查询结果
     */
    @ApiModelProperty(value = "查询结果")
    private String result;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private String timeBeg;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private String timeEnd;

}
