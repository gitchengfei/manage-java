package com.cheng.manage.common.util;

import com.cheng.manage.common.date.DateUtils;

import java.lang.reflect.Method;
import java.util.*;

/**
 * @Author: cheng fei
 * @Date: 2020/5/30 15:01
 * @Description: list 工具类
 */
public class ListUtils {

    /**
     * 作者: cheng fei
     * 创建日期: 2020/5/30 15:03
     * 描述 : List->> map 分类字段值类型为string, Number类型的
     * @param list
     * @param getMethod
     * @param <T>
     * @return
     */
    public static  <T> Map<String, List<T>> listToMapByString(List<T> list, String getMethod) {
        try {
            Map<String, List<T>> map = new HashMap<>();
            if (null != list && list.size() > 0) {
                Class<?> aClass = list.get(0).getClass();
                Method method = aClass.getMethod(getMethod);
                for (T t: list) {
                    addDateToMap(String.valueOf(method.invoke(t)), t, map);
                }
            }
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("列表转换为Map失败！");
        }
    }


    /**
     * 作者: cheng fei
     * 创建日期: 2020/5/30 15:04
     * 描述 : list -> Map 处理字段为date
     * @param list
     * @param getMethod
     * @param <T>
     * @return
     */
    public static  <T> Map<String, List<T>> listToMapByByDate(List<T> list, String getMethod, String pattern) {
        try {
            Map<String, List<T>> map = new HashMap<>();
            if (null != list && list.size() > 0) {
                Class<?> aClass = list.get(0).getClass();
                Method method = aClass.getMethod(getMethod);
                for (T t: list) {
                    Object invoke = method.invoke(t);
                    if (invoke instanceof Date) {
                        Date date = (Date) invoke;
                        String key = DateUtils.dateFormat(date, pattern);
                        addDateToMap(key, t, map);
                    } else {
                        throw new RuntimeException("需要作为Key的字段不是Date类型！");
                    }

                }
            }
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("列表转换为Map失败！");
        }
    }

    /**
     * 作者: cheng fei
     * 创建日期: 2020/5/30 15:46
     * 描述 : 将数据新增值
     * @param key
     * @param value
     * @param map
     * @param <T>
     */
    private static <T> void addDateToMap(String key, T value, Map<String, List<T>> map) {
        List<T> l = null;
        if (map.containsKey(key)) {
            l = map.get(key);
        } else {
            l = new ArrayList<T>();
            map.put(key, l);
        }
        l.add(value);
    }
}
