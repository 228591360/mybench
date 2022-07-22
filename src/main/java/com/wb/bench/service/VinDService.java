package com.wb.bench.service;

import com.alibaba.fastjson.JSONObject;
import com.wb.bench.request.OutDangerBackRequest;
import com.wb.bench.request.VinDRequest;
import com.wb.bench.response.OutDangerBackResponse;

public interface VinDService {

    JSONObject query(VinDRequest request);

    OutDangerBackResponse callback(OutDangerBackRequest request);

}
