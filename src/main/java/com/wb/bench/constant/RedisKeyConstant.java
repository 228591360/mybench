package com.wb.bench.constant;

/**
 * @ClassName RedisKeyConstant
 * @Description redis key对应常量
 * @Author lvzhenwei
 * @Date 2019/6/15 14:15
 **/
public final class RedisKeyConstant {

    private RedisKeyConstant(){

    }

    /**
     * 秒杀+积分兑换优惠券迭代---商品秒杀抢购商品信息对应key前缀
     */
    public static final String FLASH_SALE_GOODS_INFO = "flashSaleGoodsInfo:";

    /**
     * 秒杀+积分兑换优惠券迭代---商品秒杀抢购资格对应key前缀
     */
    public static final String FLASH_SALE_GOODS_QUALIFICATIONS = "flashSaleGoodsQualifications:";

    /**
     * 秒杀+积分兑换优惠券迭代---商品秒杀抢购已抢购商品数量对应key前缀
     */
    public static final String FLASH_SALE_GOODS_HAVE_PANIC_BUYING = "flashSaleGoodsHavePanicBuying:";

    /**
     * 秒杀+积分兑换优惠券迭代---商品秒杀抢购商品库存对应key前缀
     */
    public static final String FLASH_SALE_GOODS_INFO_STOCK = "flashSaleGoodsInfoStock:";

    /**
     * 生成订单快照加锁
     */
    public static final String CUSTOMER_TRADE_SNAPSHOT_LOCK_KEY = "customer:trade:snapshot:lock:";

    /**
     * redis
     */
    public static final String STORE_API_ACCESS_TOKEN = "store:api:access:token:";

    /**
     * 限流
     */
    public static final String PLATFORM_PEAK_CLIPPING = "platform:peak:clipping:";

    /**
     * 取消订单key
     */
    public static final String TRADE_CANCEL = "trade:cancel:";

    /**
     * 商城类目缓存key
     */
    public static final String STORE_CATE_LIST = "store_cate_list";

}
