package com.wb.bench.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class OutDangerRequest implements Serializable {

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
     * 回调接口
     */
    @ApiModelProperty(value = "回调接口")
    @NotBlank(message = "回调接口不能为空")
    private String callbackUrl;


    /**
     * 车牌号
     */
    @ApiModelProperty(value = "车牌号")
    private String licenseNo;

    /**
     * 发动机号
     */
    @ApiModelProperty(value = "发动机号")
    private String engineNumber;

    /**
     * 身份证号
     */
    @ApiModelProperty(value = "身份证号")
    private String idNumber;


    /**
     * 省份简称
     */
    @ApiModelProperty(value = "省份简称")
    private String province;


}
