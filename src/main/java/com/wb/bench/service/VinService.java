package com.wb.bench.service;

import com.wb.bench.request.VinRequest;

public interface VinService {
    String queryInfo(VinRequest vinRequest) throws Exception;
}
