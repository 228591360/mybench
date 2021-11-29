package com.wb.bench.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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

@Data
@Entity
@ApiModel
@AllArgsConstructor
@NoArgsConstructor
@Table( name ="recharge_record" , schema = "")
public class RechargeRecord implements Serializable {
    @Id
    @Column(name = "id", nullable = false)
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * customerId
     */
    @Column(name = "customer_id")
    private String customerId;


    /**
     * 本次充值金额
     */
    @Column(name = "recharge_account")
    private BigDecimal rechargeAccount;

    /**
     * 充值时间
     */
    @Column(name = "recharge_time")
    private LocalDateTime rechargeTime;

}
