package com.wb.bench.request;

import com.wb.bench.entity.PageEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author: WangBiao
 * @Date: 2020/12/4 10:44
 */
@Data
@ApiModel
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest extends PageEntity implements Serializable {

    /**
     * uuid
     */

    @ApiModelProperty(name = "id")
    private String id;

    /**
     * 产品名称
     */
    @ApiModelProperty(name = "产品名称")
    private String productName;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

}
