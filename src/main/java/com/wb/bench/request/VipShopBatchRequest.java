package com.wb.bench.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

@Data
public class VipShopBatchRequest implements Serializable {

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
     * 回调地址
     */
    @ApiModelProperty(value = "回调地址")
    @NotBlank(message = "回调地址")
    private String callbackUrl;

    List<ShopBatch> shop;


}
