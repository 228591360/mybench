package com.wb.bench.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class OutVinRequest implements Serializable {

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
     * 服务号
     */
    @ApiModelProperty(value = "车架号")
    private String serviceId;

    /**
     * 服务名称
     */
    @ApiModelProperty(value = "服务名称")
    private String serviceName;


}
