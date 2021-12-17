package com.wb.bench.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class VinRequest implements Serializable {

    /**
     * 客户帐号
     */
    @ApiModelProperty(value = "客户帐号")
    @NotBlank(message = "客户帐号不能为空")
    private String customerAccount;

    /**
     * 会员登录密码
     */
    @ApiModelProperty(value = "会员登录密码")
    @NotBlank(message = "会员登录密码不能为空")
    private String customerPassword;

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
     * 产品id
     */
    @ApiModelProperty(value = "产品id")
    @NotBlank(message = "产品id不能为空")
    private String productId;


    /**
     * 发动机号
     */
    @ApiModelProperty(value = "发动机号")
    private String engine;

    /**
     * 车牌号
     */
    @ApiModelProperty(value = "车牌号")
    private String carNumber;

    /**
     * 行驶证照片
     */
    @ApiModelProperty(value = "行驶证照片")
    private String imageUrl;

    /**
     * 车辆登记证照片
     */
    @ApiModelProperty(value = "车辆登记证照片")
    private String djzUrl;

}
