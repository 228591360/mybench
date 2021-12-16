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
@Table( name ="customer_product" , schema = "")
public class CustomerProduct implements Serializable {
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
     * 服务id
     */
    @Column(name = "product_id")
    private String productId;

    /**
     * 服务名称
     */
    @Column(name = "product_name")
    private String productName;

    /**
     * 服务定价
     */
    @Column(name = "product_price")
    private String productPrice;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private LocalDateTime createTime;

}
