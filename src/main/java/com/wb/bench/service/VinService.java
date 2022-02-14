package com.wb.bench.service;

import com.alibaba.fastjson.JSONObject;
import com.wb.bench.entity.BasePage;
import com.wb.bench.request.*;
import com.wb.bench.response.OutDangerBackResponse;
import com.wb.bench.response.StatisticsResponse;

public interface VinService {
    String queryInfo(VinRequest vinRequest) throws Exception;

    String queryVinInfo(VinRequest vinRequest);

    String freceivedata(String data);

    String outDange(OutVinRequest outVinRequest);

    JSONObject outDanger(OutDangerRequest request);

    OutDangerBackResponse outDangerBackData(OutDangerBackRequest request);

    BasePage<StatisticsResponse> queryPage(StatisticsRequest request);

}
