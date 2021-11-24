package com.wb.bench.redis.util;

/**
 * 支付key集合
 */
public class RedisPayUtil {

    /**
     * 支付回调-lockName
     * @param businessId
     * @return
     */
    public static String getCallbackLockName(String businessId) {
        return "CALLBACK_" + businessId;
    }
}
