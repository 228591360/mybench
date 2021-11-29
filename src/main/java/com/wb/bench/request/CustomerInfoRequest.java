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
    @ApiModelProperty(name = "uuid")
    private String customerId;

    /**
     * 公司名称
     */
    @ApiModelProperty(name = "公司名称")
    private String customerName;

    /**
     * 客户帐号
     */
    @ApiModelProperty(name = "客户帐号")
    private String customerAccount;

    /**
     * 会员登录密码
     */
    @ApiModelProperty(name = "会员登录密码")
    private String customerPassword;

    /**
     * ip地址
     */
    @ApiModelProperty(name = "ip地址")
    private String ip;

    /**
     * 客户描述
     */
    @ApiModelProperty(name = "客户描述")
    private String customerDesc;

    /**
     * 充值金额
     */
    @ApiModelProperty(name = "充值金额")
    private BigDecimal rechargeAmount;

    /**
     * 剩余金额
     */
    @ApiModelProperty(name = "剩余金额")
    private BigDecimal balanceAmount;

    /**
     * 授信额度
     */
    @ApiModelProperty(name = "授信额度")
    private BigDecimal creditAmount;

    /**
     * 创建时间
     */
    @ApiModelProperty(name = "创建时间")
    private LocalDateTime createTime;

}
