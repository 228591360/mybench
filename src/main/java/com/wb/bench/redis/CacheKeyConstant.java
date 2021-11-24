package com.wb.bench.redis;

/**
 * 缓存的key常量
 * 
 * 特别注意 该接口下 不放放与redis  不相干的属性 并且 变量的名称务必和变量的值 一样 以便启动的时候 删除redis中缓存的key
 * @author djk
 *
 */
public interface CacheKeyConstant
{
	/**
	 * 商品SKU库存的key
	 */
	String SKU_STOCK_KEY = "SKU_STOCK_KEY";

	/**
	 * 短信验证码的key 注册
	 */
	String VERIFY_CODE_KEY = "YZM_SMS_KEY";

	/**
	 * 短信验证码的key 忘记密码
	 */
	String YZM_FORGET_PWD_KEY = "YZM_FORGET_PWD_KEY";

	/**
	 * 短信验证码的key 修改密码
	 */
	String YZM_UPDATE_PWD_KEY = "YZM_UPDATE_PWD_KEY";

	/**
	 * 短信验证码的key 修改绑定手机号OLD
	 */
	String YZM_MOBILE_OLD_KEY = "YZM_MOBILE_OLD_KEY";

	/**
	 * 短信验证码的key 修改绑定手机号OLD 最后一步保存时用
	 */
	String YZM_MOBILE_OLD_KEY_AGAIN = "YZM_MOBILE_OLD_KEY_AGAIN";

	/**
	 * 短信验证码的key 修改绑定手机号NEW
	 */
	String YZM_MOBILE_NEW_KEY = "YZM_MOBILE_NEW_KEY";

	/**
	 * 邮箱验证码的key 修改绑定邮箱NEW
	 */
	String YZM_EMAIL_NEW_KEY = "YZM_EMAIL_NEW_KEY";

	/**
	 * 短信验证码的key 修改绑定邮箱O
	 */
	String YZM_MOBILE_BEFORE_EMAIL_KEY = "YZM_MOBILE_BEFORE_EMAIL_KEY";


	/**
	 * 商品分类缓存的key
	 */
	String GOODS_CATE_KEY = "GOODS_CATE_KEY";

	/**
	 * 搜索历史的key
	 */
	String SEARCH_HISTORY_KEY = "SEARCH_HISTORY_KEY";

	/**
	 * 搜索店铺历史的key
	 */
	String STORE_SEARCH_HISTORY_KEY = "STORE_SEARCH_HISTORY_KEY";

	/**
	 * 搜索分销员选品历史的key
	 */
	String DISTRIBUTE_SEARCH_HISTORY_KEY = "DISTRIBUTE_SEARCH_HISTORY_KEY";

	/**
	 * 搜索拼团商品历史的key
	 */
	String GROUPON_SEARCH_HISTORY_KEY = "GROUPON_SEARCH_HISTORY_KEY";

	/**
	 * 搜索分销推广商品历史的key
	 */
	String DISTRIBUTE_GOODS_SEARCH_HISTORY_KEY = "GROUPON_SEARCH_HISTORY_KEY";

	/**
	 * 短信上一次发送时间的key
	 */
	String YZM_MOBILE_LAST_TIME = "YZM_MOBILE_LAST_TIME";

	/**
	 * 短信上一次发送时间的key
	 */
	String YZM_EMAIL_LAST_TIME = "YZM_EMAIL_LAST_TIME";

	/**
	 * 图片验证码的key
	 */
	String KAPTCHA_KEY = "KAPTCHA_KEY";

	/**
	 * 注册错误次数KEY
	 */
	String REGISTER_ERR = "REGISTER_ERR";

	/**
	 * 登录错误次数KEY
	 */
	String CUSTOMER_LOGIN_ERR = "CUSTOMER_LOGIN_ERR";

	/**
	 * 商家注册验证码
	 */
	String YZM_SUPPLIER_REGISTER = "YZM_SUPPLIER_REGISTER";

	/**
	 * 供应商注册验证码
	 */
	String YZM_PROVIDER_REGISTER = "YZM_PROVIDER_REGISTER";

	/**
	 * s2b 平台 登录错误次数KEY
	 */
	String S2B_BOSS_LOGIN_ERR = "S2B_BOSS_LOGIN_ERR";

	/**
	 * s2b 平台 登录错误5次，账号锁定时间KEY
	 */
	String S2B_BOSS_LOCK_TIME = "S2B_BOSS_LOCK_TIME";

	/**
	 * 用户登录错误5次, 账号锁定时间key
	 */
	String CUSTOMER_LOCK_TIME = "CUSTOMER_LOCK_TIME";

	/**
	 * s2b 商家 登录错误次数KEY
	 */
	String S2B_SUPPLIER_LOGIN_ERR = "S2B_SUPPLIER_LOGIN_ERR";

	/**
	 * s2b 商家 登录错误5次，账号锁定时间KEY
	 */
	String S2B_SUPPLIER_LOCK_TIME = "S2B_SUPPLIER_LOCK_TIME";

	/**
	 * 用户
	 */
	String USER_EMPLOYEE ="USER_EMPLOYEE";

	/**
	 * 角色下的所有的功能
	 */
	String ROLE_FUNCTION = "ROLE_FUNCTION";
	/**
	 * 用户拥有的角色
	 */
	String USER_ROLE = "USER_ROLE";

	/**
	 * 微信
	 */
	String WE_CHAT="WE_CHAT";

	/**
	 * QQ
	 */
	String QQ="QQ";

	/**
	 * C 端用户登录发送验证码
	 */
	String YZM_CUSTOMER_LOGIN = "YZM_CUSTOMER_LOGIN";

	/**
	 * c 端用户微信绑定验证码
	 */
	String WX_BINDING_LOGIN = "WX_BINDING_LOGIN";

	/**
	 * c 端用户微信绑定验证码
	 */
	String QQ_BINDING_LOGIN = "QQ_BINDING_LOGIN";

	/**
	 * 弹框登录时的发送验证码
	 */
	String REGISTER_MODAL_CODE = "REGISTER_MODAL_CODE";

	/**
	 * 设置支付密码时的验证码
	 */
	String BALANCE_PAY_PASSWORD = "BALANCE_PAY_PASSWORD";

	/**
	 * 设置福利余额支付密码时的验证码
	 */
	String BALANCE_WELFARE_PAY_PASSWORD = "BALANCE_WELFARE_PAY_PASSWORD";

	/**
	 * 忘记支付密码时的验证码
	 */
	String FIND_BALANCE_PAY_PASSWORD = "FIND_BALANCE_PAY_PASSWORD";

	/**
	 * 忘记福利余额支付密码时的验证码
	 */
	String FIND_BALANCE_WELFARE_PAY_PASSWORD = "FIND_BALANCE_WELFARE_PAY_PASSWORD";

	/**
	 * APP设置支付密码时的验证码
	 */
	String YZM_APP_BALANCE_PAY_PASSWORD = "YZM_APP_BALANCE_PAY_PASSWORD";

	/**
	 * APP设置福利余额支付密码时的验证码
	 */
	String YZM_APP_BALANCE_WELFARE_PAY_PASSWORD = "YZM_APP_BALANCE_WELFARE_PAY_PASSWORD";

	/**
	 * APP忘记支付密码时的验证码
	 */
	String YZM_APP_FIND_BALANCE_PAY_PASSWORD = "YZM_APP_FIND_BALANCE_PAY_PASSWORD";

	/**
	 * APP忘记福利余额支付密码时的验证码
	 */
	String YZM_APP_FIND_BALANCE_WELFARE_PAY_PASSWORD = "YZM_APP_FIND_BALANCE_WELFARE_PAY_PASSWORD";

    /**
     * 敏感词
     */
	String BAD_WORD = "BAD_WORD";

	/**
	 * 商品评价系数
	 */
	String EVALUATE_RATIO = "EVALUATE_RATIO";

	/**
	 * 通过小程序登录的登录信息
	 */
	String WEAPP_LOGIN_INFO = "WEAPP_LOGIN_INFO";

	/**
	 * 会员导入成功发送通知
	 */
	String IMPORT_CUSTOMER = "IMPORT_CUSTOMER";

	/**
	 * saas主域名
	 */
	String SAAS_DOMAIN = "SAAS_DOMAIN";

	/**
	 * 门店小程序配置
	 */
	String STORE_WECHAT_MINI_PROGRAM_CONFIG = "STORE_WECHAT_MINI_PROGRAM_CONFIG";

	/**
	 * 微信小程序AccessToken
	 */
	String WECHAT_SMALL_PROGRAM_ACCESS_TOKEN = "WECHAT_SMALL_PROGRAM_ACCESS_TOKEN:";

	/**
	 * 门店微信分享配置
	 */
	String STORE_WECHAT_SHARE_SET = "STORE_WECHAT_SHARE_SET";

	/**
	 * 友盟配置信息
	 */
	String UMENG_CONFIG = "UMENG_CONFIG";

	/**
	 * redis 缓存saas域名配置信息
	 */
	String PC_SAAS_KEY = "PC_SAAS_KEY";

	String APP_OR_MINI_SAAS_KEY = "APP_OR_MINI_SAAS_KEY";

	String MOBILE_SAAS_KEY = "MOBILE_SAAS_KEY";

	/**
	 * redis 缓存saas域名配置信息, 提供给Nginx配置使用
	 */
	String CACHE_PC_KEY_FOR_NGINX = "SAAS_PC_KEY";

	/**
	 * redis 缓存自定义全域名配置信息, 提供给Nginx配置使用
	 */
	String CACHE_PC_FULL_KEY_FOR_NGINX = "SAAS_PC_KEY_FULL";

	/**
	 * redis 缓存saas域名配置信息, 提供给Nginx配置使用
	 */
	String CACHE_MOBILE_KEY_FOR_NGINX = "SAAS_MOBILE_KEY";

	/**
	 * redis 缓存自定义全域名配置信息, 提供给Nginx配置使用
	 */
	String CACHE_MOBILE_FULL_KEY_FOR_NGINX = "SAAS_MOBILE_KEY_FULL";

	/**
	 * 采购申请提醒商家
	 */
	String PURCHASE_APPLY_BUSINESS_REMIND="PURCHASE_APPLY_BUSINESS_REMIND";

	/**
	 * 采购申请提醒商家
	 */
	String GROUPON_PLATFORM_EXPORT_GOODS_LIST="Q_GROUPON_PLATFORM_EXPORT_GOODS_LIST";

	/**
	 * 邮箱验证码的key 忘记密码
	 */
	String EMAIL_FORGET_PWD_KEY = "EMAIL_FORGET_PWD_KEY";

	/**
	 * 邮箱验证码的key 修改密码
	 */
	String EMAIL_UPDATE_PWD_KEY = "EMAIL_UPDATE_PWD_KEY";

	/**
	 * 邮箱验证码的key 注册
	 */
	String VERIFY_EMAIL_CODE_KEY = "YZM_EMAIL_KEY";

	/**
	 * 邮箱验证码的key 修改支付密码
	 */
	String EMAIL_UPDATE_PAY_PWD_KEY = "EMAIL_UPDATE_PAY_PWD_KEY";

	/**
	 *  库存预警值
	 */
	String INVENTORY_WARNING_VALUE = "INVENTORY_WARNING_VALUE";

	/**
	 * 体验账号有效期
	 */
	String EXPERIENCE_ACCOUNT_VALID_PERIOD = "EXPERIENCE_ACCOUNT_VALID_PERIOD";

	/**
	 * 商城试用申请验证码
	 */
	String YZM_STORE_TRIAL_KEY = "YZM_STORE_TRIAL_KEY";
	/**
	 * 商城试用申请验证码错误次数
	 */
	String YZM_STORE_TRIAL_ERR = "YZM_STORE_TRIAL_ERR";

	/**
	 * 超期停用现结支付开关key
	 */
	String OVERDUE_PAY_CONTROL = "OVERDUE_PAY_CONTROL";
}
