package org.gourd.hu.core.utils;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
 
/**
 * 集合复制工具类
 *
 * @author gourd.hu
 */
public class CollectionUtil {

    /**
     * 修改集合中的数据类型
     * @param list
     * @param tClass
     * @param <T>
     * @return
     */
    public static <T> List copyList(List<T> list, Class tClass) {
        if (CollectionUtils.isEmpty(list)) {
            return new ArrayList();
        }
        return JSON.parseArray(JSON.toJSONString(list), tClass);
    }

    /**
     * map转换为object
     * @param map
     * @return
     */
    public static Object copyMap(Map map) {
        return JSON.parseObject(JSON.toJSONString(map));

    }

    /**
     * 将对象装换为map
     * @param bean
     * @return
     */
    public static <T> Map<String, Object> beanToMap(T bean) {
        Map<String, Object> map = Maps.newHashMap();
        if (bean != null) {
            BeanMap beanMap = BeanMap.create(bean);
            for (Object key : beanMap.keySet()) {
                map.put(key+"", beanMap.get(key));
            }
        }
        return map;
    }


    /**
     * 将List<T>转换为List<Map<String, Object>>
     * @param objList
     * @return
     */
    public static <T> List<Map<String, Object>> objectsToMaps(List<T> objList) {
        List<Map<String, Object>> list = Lists.newArrayList();
        if (objList != null && objList.size() > 0) {
            Map<String, Object> map = null;
            T bean = null;
            for (int i = 0,size = objList.size(); i < size; i++) {
                bean = objList.get(i);
                map = beanToMap(bean);
                list.add(map);
            }
        }
        return list;
    }

}
