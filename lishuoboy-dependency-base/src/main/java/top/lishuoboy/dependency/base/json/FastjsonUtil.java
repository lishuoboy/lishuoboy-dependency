package top.lishuoboy.dependency.base.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.List;
import java.util.Map;

/**
 * JSON 工具类
 *
 * @author lishuoboy
 * @date 2022-2-8
 */
public class FastjsonUtil {

    public static String toJsonStr(Object obj) {
        return JSON.toJSONString(obj);
    }

    public static String toJsonPrettyStr(Object obj) {
        return JSON.toJSONString(obj, true);
    }

    public static <T> T toBean(String jsonStr, Class<T> beanClass) {
        return JSON.parseObject(jsonStr, beanClass);
    }

    public static <T> List<T> toList(String jsonStr, TypeReference<List<T>> typeReference) {
        return JSON.parseObject(jsonStr, typeReference.getType());
    }

    public static <T> List<T> toList(String jsonStr, Class<T> beanClass) {
        return JSON.parseArray(jsonStr, beanClass);
    }

    public static <K, V> Map<K, V> toMap(String jsonStr, TypeReference<Map<K, V>> typeReference) {
        return JSON.parseObject(jsonStr, typeReference.getType());
    }

    public static Map<String, Object> toMap(String jsonStr) {
        return toMap(jsonStr, new com.alibaba.fastjson.TypeReference<Map<String, Object>>() {
        });
    }
}