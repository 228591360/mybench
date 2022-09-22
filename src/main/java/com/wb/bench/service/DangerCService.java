package com.wb.bench.service;

import com.alibaba.fastjson.JSONObject;
import com.wb.bench.request.DangerCRequest;
import com.wb.bench.request.OutDangerBackRequest;
import com.wb.bench.response.OutDangerBackResponse;

public interface DangerCService {

    JSONObject query(DangerCRequest request);

    OutDangerBackResponse queryBackData(OutDangerBackRequest request);

}
