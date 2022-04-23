package top.lishuoboy.dependency.base.json;

import cn.hutool.core.lang.TypeReference;
import cn.hutool.json.JSONConfig;
import cn.hutool.json.JSONUtil;

import java.util.List;
import java.util.Map;

/**
 * JSON 工具类
 *
 * @author lishuoboy
 * @date 2022-4-22
 */
public class HuJsonUtil {
    private static JSONConfig jsonConfig = new JSONConfig();

    static {
        jsonConfig.setOrder(true);
//        jsonConfig.setDateFormat(DatePattern.UTC_PATTERN);
    }

    public static String toJsonStr(Object obj) {
        return JSONUtil.toJsonStr(obj, jsonConfig);
    }

    public static String toJsonPrettyStr(Object obj) {
        return JSONUtil.formatJsonStr(toJsonStr(obj));   // JSONUtil.toJsonPrettyStr(obj) 不能传参jsonConfig
    }

    public static <T> T toBean(String jsonStr, Class<T> beanClass) {
        return JSONUtil.toBean(jsonStr, beanClass);
    }

    public static <T> List<T> toList(String jsonStr, TypeReference<List<T>> typeReference) {
        return JSONUtil.toBean(jsonStr, typeReference, false);
    }

    public static <T> List<T> toList(String jsonStr, Class<T> beanClass) {
        return JSONUtil.toList(jsonStr, beanClass);
    }

    public static <K, V> Map<K, V> toMap(String jsonStr, TypeReference<Map<K, V>> typeReference) {
        return JSONUtil.toBean(jsonStr, typeReference.getType(), false);
    }

    public static Map<String, Object> toMap(String jsonStr) {
        return JSONUtil.toBean(jsonStr, new TypeReference<Map<String, Object>>() {
        }.getType(), false);
    }

}