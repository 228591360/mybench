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
import java.time.LocalDateTime;

@Data
@Entity
@ApiModel
@AllArgsConstructor
@NoArgsConstructor
@Table( name ="wb_query_log" , schema = "")
public class WbQueryLog implements Serializable {
    @Id
    @Column(name = "id", nullable = false)
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 产品名称
     */
    @Column(name = "product_name")
    private String productName;

    /**
     * 订单ID
     */
    @Column(name = "order_id")
    private String orderId;

    /**
     * 回调地址
     */
    @Column(name = "call_back_url")
    private String callBackUrl;

    /**
     * customerId
     */
    @Column(name = "customer_id")
    private String customerId;

    /**
     * 客户名称
     */
    @Column(name = "customer_name")
    private String customerName;

    /**
     * 收费
     */
    @Column(name = "toll")
    private String toll;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private LocalDateTime createTime;

}
