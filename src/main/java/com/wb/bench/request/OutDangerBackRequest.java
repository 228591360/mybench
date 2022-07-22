package com.wb.bench.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class OutDangerBackRequest implements Serializable {

    private String encrypt;

    private String encryptType;

    private String sign;



}
