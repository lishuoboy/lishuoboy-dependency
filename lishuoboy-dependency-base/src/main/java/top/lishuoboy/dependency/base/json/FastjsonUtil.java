package top.lishuoboy.dependency.base.json;

import com.alibaba.fastjson.JSON;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Fastjson 工具类
 *
 * @author lishuoboy
 * @date 2022-2-8
 */
public class FastjsonUtil {

    public static String bean2Str(Object obj) {
        return JSON.toJSONString(obj);
    }

    public static String bean2PrettyStr(Object obj) {
        return JSON.toJSONString(obj, true);
    }

    public static <T> T str2Bean(String jsonStr, Class<T> beanType) {
        T result = JSON.parseObject(jsonStr, beanType);
        return result;
    }

    public static <T> List<T> str2List(String jsonStr, Class<T> beanType) {
        List<T> resultList = JSON.parseArray(jsonStr, beanType);
        return resultList;
    }

    /** 固定泛型 */
    public static LinkedHashMap<String, Object> str2Map(String jsonStr) {
        LinkedHashMap<String, Object> resultMap = JSON.parseObject(jsonStr, LinkedHashMap.class);
        return resultMap;
    }
}