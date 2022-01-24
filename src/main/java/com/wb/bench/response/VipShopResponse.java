package com.wb.bench.response;
import io.swagger.annotations.ApiModel;
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
public class VipShopResponse implements Serializable {

    private Integer code;

    private String msg;

}
