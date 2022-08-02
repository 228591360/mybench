package com.wb.bench.service;

import com.wb.bench.request.OutDangerBackRequest;
import com.wb.bench.request.VipShopBRequest;
import com.wb.bench.request.VipShopCallbackRequest;
import com.wb.bench.request.VipShopRequest;
import com.wb.bench.response.OutDangerBackResponse;
import com.wb.bench.response.VipShopResponse;

public interface VipShopBService {

    String weiBaoQX(VipShopRequest vipShopRequest);

    OutDangerBackResponse callback(OutDangerBackRequest request);

    String weiBao(VipShopBRequest vipShopBRequest);

    VipShopResponse callbackData(VipShopCallbackRequest vipShopCallbackRequest);

}
