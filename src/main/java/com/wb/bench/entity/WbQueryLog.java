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
     * 产品id
     */
    @Column(name = "product_id")
    private String productId;

    /**
     * 产品名称
     */
    @Column(name = "product_name")
    private String productName;

    /**
     * 车架
     */
    @Column(name = "vin")
    private String vin;

    /**
     * 发动机号
     */
    @Column(name = "engine_no")
    private String engineNo;

    /**
     * 车牌号
     */
    @Column(name = "license_no")
    private String licenseNo;

    /**
     * 行驶证照片
     */
    @Column(name = "license_url")
    private String licenseUrl;

    /**
     * 车辆登记证照片
     */
    @Column(name = "registration_url")
    private String registrationUrl;

    /**
     * 01: 大车 02: 小车 15: 挂车 51: 新能源大车 52: 新能源小车
     */
    @Column(name = "car_type")
    private String carType;

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
     * 查询结果
     */
    @Column(name = "result")
    private String result;


    /**
     * 备注
     */
    @Column(name = "remark")
    private String remark;

    /**
     * 回调时间
     */
    @Column(name = "back_time")
    private LocalDateTime backTime;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private LocalDateTime createTime;

}
