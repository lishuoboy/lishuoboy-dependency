package top.lishuoboy.dependency.base.json;

import cn.hutool.core.util.ArrayUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.util.List;
import java.util.Map;

/**
 * JSON 工具类
 *
 * @author lishuoboy
 * @date 2022-2-8
 */
public class FastjsonUtil {
    // 默认的 featureList，提供外部使用
    private static final SerializerFeature[] defaultFeatureArr = new SerializerFeature[]{
//        SerializerFeature.DisableCircularReferenceDetect,   // 禁用自引用
        SerializerFeature.SortField,                // 启用 Bean按照key名称自然排序
        SerializerFeature.MapSortField,             // 启用 Map 按照key名称自然排序
        SerializerFeature.WriteMapNullValue,        // 输出null 对象
        SerializerFeature.IgnoreErrorGetter         // 忽略get异常
    };

    /** fastjson SerializerFeature详解  https://blog.csdn.net/lishuoboy/article/details/124394291 */
    public static String toJsonStr(Object obj, boolean isUseDefaultFeatures, SerializerFeature... features) {
        if (isUseDefaultFeatures) {
            return JSON.toJSONString(obj, ArrayUtil.distinct(ArrayUtil.addAll(defaultFeatureArr, features)));
        }
        return JSON.toJSONString(obj, features);
    }

    /** fastjson SerializerFeature详解  https://blog.csdn.net/lishuoboy/article/details/124394291 */
    public static String toJsonStr(Object obj) {
        return toJsonStr(obj, true);
    }

    public static String toJsonPrettyStr(Object obj) {
        return toJsonStr(obj, true, SerializerFeature.PrettyFormat);
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
        return toMap(jsonStr, new TypeReference<Map<String, Object>>() {
        });
    }
}