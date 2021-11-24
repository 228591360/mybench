package com.wb.bench.util;

import org.apache.commons.lang3.StringUtils;

/**
 * 字符串工具类
 * Created by pingchen on 15/12/29.
 */
public final class StringUtil {

    public static String SQL_LIKE_CHAR = "%";

    public static Character SQL_LIKE_ESCAPECHAR = '\\';

    /**
     * 对象转换类型
     * @param object 值
     * @param clazz 对象
     * @return 返回值，否则为null
     */
    public static <T> T cast(Object object, Class<T> clazz){
        if(object != null && clazz.isInstance(object)) {
            return clazz.cast(object);
        }
        return null;
    }

    /**
     * 数组转换类型
     * @param objects 对象数组
     * @param index 索引
     * @param clazz 类型
     * @return 返回值，否则为null
     */
    public static <T> T cast(Object[] objects, int index , Class<T> clazz){
        return cast(objects[index], clazz);
    }

    public static String trunc(String str, int len, String afterPrefix) {
        if (StringUtils.isNotBlank(str) && str.length() > len) {
            return str.substring(0, len).concat(afterPrefix);
        }
        return str;
    }
}
