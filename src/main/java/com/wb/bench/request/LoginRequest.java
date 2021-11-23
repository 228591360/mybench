package com.wb.bench.request;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;


/**
 * @Author: WangBiao
 * @Date: 2020/12/4 10:44
 */
@Data
@ApiModel
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest implements Serializable {

    /**
     * 客户帐号
     */
    @ApiModelProperty(name = "客户帐号")
    @NotBlank(message = "客户帐号不能为空")
    private String customerAccount;

    /**
     * 会员登录密码
     */
    @ApiModelProperty(name = "会员登录密码")
    @NotBlank(message = "会员登录密码不能为空")
    private String customerPassword;


}
