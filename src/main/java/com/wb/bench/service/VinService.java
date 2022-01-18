package com.wb.bench.service;

import com.wb.bench.entity.BasePage;
import com.wb.bench.request.OutVinRequest;
import com.wb.bench.request.StatisticsRequest;
import com.wb.bench.request.VinRequest;
import com.wb.bench.response.StatisticsResponse;

public interface VinService {
    String queryInfo(VinRequest vinRequest) throws Exception;

    String queryVinInfo(VinRequest vinRequest);

    String freceivedata(String data);

    String outDange(OutVinRequest outVinRequest);

    BasePage<StatisticsResponse> queryPage(StatisticsRequest request);

}
