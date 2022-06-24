package com.wb.bench.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class InquireRequest implements Serializable {

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
     * 行驶证照片
     */
    @ApiModelProperty(value = "行驶证照片")
    @NotBlank(message = "行驶证照片不能为空")
    private String imageUrl;


    /**
     * 车牌号
     */
    @ApiModelProperty(value = "车牌号")
    private String licenseNo;


    /**
     * 查询订单 ID
     */
    @ApiModelProperty(value = "查询订单 ID")
    @NotBlank(message = "查询订单 ID不能为空")
    private String orderId;


}
