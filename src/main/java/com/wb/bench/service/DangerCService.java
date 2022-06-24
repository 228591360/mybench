package com.wb.bench.service;

import com.alibaba.fastjson.JSONObject;
import com.wb.bench.request.InquireRequest;
import com.wb.bench.request.PlaceAnOrderRequest;

public interface DangerCService {



    JSONObject inquire(InquireRequest request);


    JSONObject  placeAnOrder(PlaceAnOrderRequest request);

}
