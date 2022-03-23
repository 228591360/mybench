package com.wb.bench.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class Danger implements Serializable {


    /**
     * 车架号
     */
    @ApiModelProperty(value = "车架号")
    private String vin;

    /**
     * 车牌号
     */
    @ApiModelProperty(value = "车牌号")
    private String licenseNo;

    /**
     * 发动机号
     */
    @ApiModelProperty(value = "发动机号")
    private String engineNumber;

    /**
     * 身份证号
     */
    @ApiModelProperty(value = "身份证号")
    private String idNumber;


    /**
     * 省份简称
     */
    @ApiModelProperty(value = "省份简称")
    private String province;


}
