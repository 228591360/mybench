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
@Table( name ="customer_service" , schema = "")
public class CustomerService implements Serializable {
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
    @Column(name = "service_id")
    private String serviceId;

    /**
     * 服务名称
     */
    @Column(name = "service_name")
    private String serviceName;

    /**
     * 服务定价
     */
    @Column(name = "service_price")
    private String servicePrice;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private LocalDateTime createTime;

}
