package com.wb.bench.VO;

import lombok.Data;

import java.io.Serializable;

@Data
public class DangerCVO implements Serializable {
    private String claimstatus;
    private String lossoccurdate;
    private String losspart;
    private String vehiclelossamount;
    private String optioncause;
    private String accidentliab;
    private String isrobber;
    private String ishotsincedetonation;
    private String iswaterflooded;
    private String cxzl;
}
