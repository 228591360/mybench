package com.wb.bench.util;


import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * 增加一些Java8中不支持的集合操作方法
 * Created by liuzhaoming on 15/12/2.
 */
@SuppressWarnings("unused")
public class IteratorUtils {

    /**
     * 收集对象中的Key，过滤掉重复值
     *
     * @param coll 要收集的对象
     * @param <K>  返回的Key类型
     * @param <V>  要收集对象的对象类型
     * @return Key集合
     */
    public static <K, V> List<K> collectKey(Collection<V> coll, Function<V, K> keyGetter) {
        if (CollectionUtils.isEmpty(coll)) {
            return Collections.emptyList();
        }

        return coll.stream().map(keyGetter).distinct().collect(Collectors.toList());
    }

    /**
     * 批量收集对象中的Key，过滤掉重复值
     *
     * @param coll 要收集的对象
     * @param <K>  返回的Key类型
     * @param <V>  要收集对象的对象类型
     * @return Key集合
     */
    public static <K, V> List<K> collectFlatKey(Collection<V> coll, Function<V, List<K>> keyGetter) {
        if (CollectionUtils.isEmpty(coll)) {
            return Collections.emptyList();
        }

        return coll.stream().flatMap(item -> keyGetter.apply(item).stream()).distinct().collect(Collectors.toList());
    }

    /**
     * 根据指定Key分组
     * 如果存在一个描述人的类Person(id, name, area)，给定3个人(1, "Tom", "南京")，(2, "Cate", "南京")，(3, "Lily", "北京")
     * 那么希望给这三个人根据地域（area）进行分组，只需调用方法
     * groupBy(Arrays.asList(person1, person2, person3), Person::getArea)
     * 返回值为Map:{"南京" : [person1, person2], "北京" : [person3]}
     *
     * @param coll      要进行分组的集合
     * @param keyGetter 根据集合中的元素获取分组的Key
     * @param <K>       分组的Key类型
     * @param <V>       集合元素
     * @return 分好组的Map
     */
    public static <K, V> Map<K, List<V>> groupBy(Collection<V> coll, Function<V, K> keyGetter) {
        if (CollectionUtils.isEmpty(coll)) {
            return Collections.emptyMap();
        }
        return coll.stream().collect(Collectors.groupingBy(keyGetter));
    }


    /**
     * 生成一个序列，类似于python的range函数
     *
     * @param size 范围
     * @return [1, 2, 3, 4, 5, 6, ... , size-1]
     */
    public static List<Long> range(long size) {
        if (size <= 1) {
            return Collections.emptyList();
        }

        List<Long> valueList = new ArrayList<>();
        for (long i = 0; i < size; i++) {
            valueList.add(i);
        }

        return valueList;
    }

    public static  <T>  List<T> range(long size,Class<T> t){
        if (size <= 1) {
            return Collections.emptyList();
        }
        List<T> valueList = new ArrayList<>();
        for (long i = 0; i < size; i++) {
            try {
                valueList.add(t.newInstance());
            } catch (Exception e) {
                e.printStackTrace();
                valueList.add(null);
            }
        }
        return valueList;
    }

    /**
     * 集合对象去重
     * @param keyExtractor
     * @param <T>
     * @return 作为去重过滤条件返回
     */
    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }
}
