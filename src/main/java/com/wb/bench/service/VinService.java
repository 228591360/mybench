package com.wb.bench.service;

import com.wb.bench.request.OutVinRequest;
import com.wb.bench.request.VinRequest;

public interface VinService {
    String queryInfo(VinRequest vinRequest) throws Exception;

    String queryVinInfo(VinRequest vinRequest);

    String freceivedata(String data);

    String outDange(OutVinRequest outVinRequest);
}
