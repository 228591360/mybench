package com.wb.bench.service;

import com.alibaba.fastjson.JSONObject;
import com.wb.bench.request.OutDangerBatchRequest;
import com.wb.bench.request.OutVinBatchRequest;

public interface VinBatchService {


    String outDange(OutVinBatchRequest request);

    JSONObject outDanger(OutDangerBatchRequest request);




}
