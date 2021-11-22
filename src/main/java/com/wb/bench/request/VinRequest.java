package com.wb.bench.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class VinRequest implements Serializable {
    /**
     * 车架号
     */
    private String vin;

}
