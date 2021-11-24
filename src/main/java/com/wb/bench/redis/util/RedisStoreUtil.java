package com.wb.bench.redis.util;
import com.wb.bench.redis.CacheKeyConstant;

/**
 * 门店缓存key集合
 */
public class RedisStoreUtil {

    /**
     * 小程序码AccessToken
     *
     * @param storeId
     * @return
     */
    public static String getWechatAccessToken(Long storeId) {
        return CacheKeyConstant.WECHAT_SMALL_PROGRAM_ACCESS_TOKEN + storeId;
    }
}
