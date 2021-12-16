package com.wb.bench.entity;
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

/**
 * @Author: WangBiao
 * @Date: 2020/12/4 10:44
 */
@Data
@Entity
@ApiModel
@AllArgsConstructor
@NoArgsConstructor
@Table( name ="product" , schema = "")
public class Product implements Serializable {

    /**
     * uuid
     */
    @Id
    @TableId("id")
    @Column(name = "id")
    private String id;

    /**
     * 产品名称
     */
    @Column(name = "product_name")
    private String productName;


    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private LocalDateTime createTime;

}
