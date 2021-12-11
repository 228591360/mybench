package com.wb.bench.request;

import com.wb.bench.entity.PageEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @Author: WangBiao
 * @Date: 2020/12/4 10:44
 */
@Data
@ApiModel
@AllArgsConstructor
@NoArgsConstructor
public class CustomerInfoRequest extends PageEntity implements Serializable {

    /**
     * uuid
     */
    @ApiModelProperty(value = "客户帐号")
    private String customerId;

    /**
     * 公司名称
     */
    @ApiModelProperty(value = "客户名称")
    private String customerName;

    /**
     * 客户帐号
     */
    @ApiModelProperty(value = "客户帐号")
    private String customerAccount;

    /**
     * 会员登录密码
     */
    @ApiModelProperty(value = "会员登录密码")
    private String customerPassword;

    /**
     * ip地址
     */
    @ApiModelProperty(value = "ip地址")
    private String ip;

    /**
     * 客户描述
     */
    @ApiModelProperty(value = "客户描述")
    private String customerDesc;

    /**
     * 充值金额
     */
    @ApiModelProperty(value = "充值金额")
    private BigDecimal rechargeAmount;

    /**
     * 剩余金额
     */
    @ApiModelProperty(value = "剩余金额")
    private BigDecimal balanceAmount;

    /**
     * 授信额度
     */
    @ApiModelProperty(value = "授信额度")
    private BigDecimal creditAmount;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

}
