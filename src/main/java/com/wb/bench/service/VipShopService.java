package com.wb.bench.service;

import com.wb.bench.request.VipShopCallbackRequest;
import com.wb.bench.request.VipShopRequest;
import com.wb.bench.response.VipShopResponse;

public interface VipShopService {

    String weiBao(VipShopRequest vipShopRequest);

    VipShopResponse callbackData(VipShopCallbackRequest vipShopCallbackRequest);

}
