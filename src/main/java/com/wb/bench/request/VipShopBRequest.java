package com.wb.bench.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class VipShopBRequest implements Serializable {


    /**
     * 车架号
     */
    @ApiModelProperty(value = "车架号")
    @NotBlank(message = "车架号不能为空")
    private String vin;

    /**
     * 回调地址
     */
    @ApiModelProperty(value = "回调地址")
    @NotBlank(message = "回调地址")
    private String callbackUrl;


    /**
     * 发动机号
     */
    @ApiModelProperty(value = "发动机号")
    private String engineNo;

    /**
     * 车牌号
     */
    @ApiModelProperty(value = "车牌号")
    private String licenseNo;

    /**
     * 行驶证照片
     */
    @ApiModelProperty(value = "行驶证照片")
    private String licenseUrl;

    /**
     * 车辆登记证照片
     */
    @ApiModelProperty(value = "车辆登记证照片")
    private String registrationUrl;

    /**
     * 01: 大车 02: 小车 15: 挂车 51: 新能源大车 52: 新能源小车
     */
    @ApiModelProperty(value = "01: 大车 02: 小车 15: 挂车 51: 新能源大车 52: 新能源小车")
    private String carType;


    private String customerId;

    private String customerName;

    private String orderId;


}
