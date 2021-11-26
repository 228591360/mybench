package com.wb.bench.entity;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @Author: WangBiao
 * @Date: 2020/12/4 10:44
 */
@Data
@Entity
@ApiModel
@AllArgsConstructor
@NoArgsConstructor
@Table( name ="customer_info" , schema = "")
public class CustomerInfo implements Serializable {

    /**
     * uuid
     */
    @Id
    @Column(name = "customer_id")
    private Long customerId;

    /**
     * 公司名称
     */
    @Column(name = "customer_name")
    private String customerName;

    /**
     * 客户帐号
     */
    @Column(name = "customer_account")
    private String customerAccount;

    /**
     * 会员登录密码
     */
    @Column(name = "customer_password")
    private String customerPassword;

    /**
     * ip地址
     */
    @Column(name = "ip")
    private String ip;

    /**
     * 客户描述
     */
    @Column(name = "customer_desc")
    private String customerDesc;

    /**
     * 充值金额
     */
    @Column(name = "recharge_amount")
    private BigDecimal rechargeAmount;

    /**
     * 剩余金额
     */
    @Column(name = "balance_amount")
    private BigDecimal balanceAmount;

    /**
     * 授信额度
     */
    @Column(name = "credit_amount")
    private BigDecimal creditAmount;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private LocalDateTime createTime;

}
