package com.wb.bench.common;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Data
@Component
public class ProductCode implements Serializable {

    /**
     * 维保
     */
    private String wbCode="8000017dbeebc5da435431bf078176c7";

    /**
     * 出险
     */
    private String chuXianCode="8000017dbeebf35460805819fef4288d";

    /**
     * 异步出险
     */
    private String YiBuChuXianCode="8000017dbeebf35460805819fef4288g";

    /**
     * 唯品维保
     */
    private String vipShopWBCode="8000017dbeebf35460805819fef4288f";

}
