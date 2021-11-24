package com.wb.bench.util;

/**
 * <p>公共异常码定义</p>
 * Created by of628-wenzhi on 2018-06-21-下午2:58.
 */
public final class CommonErrorCode {
    /**
     * 指定异常，不走国际化，异常信息由B2bRuntimeException字段result设定
     */
    public final static String
            SPECIFIED = "K-999999";


    /**
     * 针对我们的业务权限
     */
    public final static String METHOD_NOT_ALLOWED = "K-999998";


    /**
     * 重复提交
     */
    public final static String REPEAT_REQUEST = "K-999997";

    /**
     * 重复提交
     */
    public final static String INCLUDE_BAD_WORD = "K-999996";

    /**
     * 操作成功
     */
    public final static String SUCCESSFUL = "操作成功";

    /**
     * 操作失败
     */
    public final static String FAILED = "操作失败";

    /**
     * 账号已被禁用
     */
    public final static String EMPLOYEE_DISABLE = "K-000005";

    /**
     * 参数错误
     */
    public final static String PARAMETER_ERROR = "参数错误";

    /**
     * 验证码错误
     */
    public final static String VERIFICATION_CODE_ERROR = "K-000010";

    /**
     * 上传文件失败
     */
    public final static String UPLOAD_FILE_ERROR = "K-000011";

    /**
     * 发送失败
     */
    public final static String SEND_FAILURE = "K-000012";

    /**
     * 您没有权限访问
     */
    public final static String PERMISSION_DENIED = "K-000014";


    /**
     * 非法字符
     */
    public final static String ILLEGAL_CHARACTER = "非法字符";


    /**
     * 小程序被禁用，请检查设置
     */
    public final static String WEAPP_FORBIDDEN = "K-000024";


    /**
     * 阿里云连接异常
     */
    public final static String ALIYUN_CONNECT_ERROR = "K-090702";

    /**
     * 阿里云上传图片失败
     */
    public final static String ALIYUN_IMG_UPLOAD_ERROR = "K-090703";

    private CommonErrorCode() {
    }

    /**
     * 系统未知错误
     */
    public static final String SYSTEM_UNKNOWN_ERROR = "system-unknow-error";


    /**
     * 常用物流公司数量超限错误
     */
    public static final String EXPRESS_MAX_COUNT_ERROR = "K-090901";

    /**
     * 再次购买时商品异常状态
     * {0}{1}，无法加入购物车
     */
    public static final String FAILD_ADD_PURCHASE_REBUY = "K-050319";

    /**
     * 再次购买订单多个商品存在多种异常状态
     * 存在无效商品，无法加入购物车
     */
    public static final String MANY_ERROR_FAILD_ADD_PURCHASE_REBUY = "K-050320";

    /**
     * 会计中心
     */
    public final static String ACCOUNTING_SUBJECT_NEED_ERROR = "K-233009";

    /**
     * 成本中心
     */
    public final static String COST_CENTER_NEED_ERROR = "K-233010";


    /**
     * 审批设置闭环error
     */
    public final static String APPROVE_LOOP_ERROR = "K-300001";

    /**
     * 请填写验证码
     */
    public final static String NEED_VERIFICATION_CODE = "K-000025";

    /**
     * 微信首次登录
     */
    public final static String WX_ERROR = "K-900001";

    /**
     * 签名不匹配
     */
    public final static String KEY_ISNOTMUTH = "K-99999";

    /**
     * 削峰限流-导入校验失败 会返回校验失败文件resourceKey
     */
    public final static String KEY_CURRENT_LIMITING = "K-000000-1";

    /**
     * 运费模板错误
     */
    public final static String FREIGHT_ERROR = "K-002001";

    /**
     * 运费模板计费规则错误
     */
    public final static String FREIGHT_RULE_ERROR = "K-002002";

    /**
     * 运费模板订单等级错误
     */
    public final static String FREIGHT_LEVEL_ERROR = "K-002003";

    /**
     * 没有订单金额
     */
    public final static String FREIGHT_ORDER_PRICE_ERROR = "K-002004";

    /**
     * 民生支付失败、重复支付
     */
    public final static String KEY_PAY_FAILED_NOT_CANCEL = "K-100208";

    /**
     * 民生重复支付,需要取消交易
     */
    public final static String KEY_REPAY_FAILED = "K-100209";

    /**
     * 民生重复支付,需要取消交易
     */
    public final static String KEY_FAILED_CANCEL = "K-100210";

    /**
     * 民生支付失败,需要取消交易
     */
    public final static String KEY_PAY_FAILED_CANCEL = "K-100211";

}
