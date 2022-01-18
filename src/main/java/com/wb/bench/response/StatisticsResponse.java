package com.wb.bench.response;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: WangBiao
 * @Date: 2020/12/4 10:44
 */
@Data
@ApiModel
@AllArgsConstructor
@NoArgsConstructor
public class StatisticsResponse implements Serializable {

    /**
     * 公司名称
     */
    @ApiModelProperty(value = "公司名称")
    private String customerName;

    /**
     * 产品名称
     */
    @ApiModelProperty(value = "产品名称")
    private String productName;

    /**
     * 收费条数
     */
    @ApiModelProperty(value = "收费条数")
    private String toll;

    /**
     * 总条数
     */
    @ApiModelProperty(value = "总条数")
    private String total;

}
