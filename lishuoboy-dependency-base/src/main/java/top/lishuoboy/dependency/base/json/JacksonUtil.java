package top.lishuoboy.dependency.base.json;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

import java.util.List;
import java.util.Map;

/**
 * JSON 工具类
 *
 * @author lishuoboy
 * @date 2022-1-10
 */
public class JacksonUtil {
    private static ObjectMapper mapper = new ObjectMapper();

    static {
//        JacksonUtil.mapper = mapper.registerModules(new JavaTimeModule());  // JavaTimeModule 支持 LocalDateTime、Instant
//        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    }

    @SneakyThrows
    public static String toJsonStr(Object obj) {
        return mapper.writeValueAsString(obj);
    }

    @SneakyThrows
    public static String toJsonPrettyStr(Object obj) {
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
    }

    @SneakyThrows
    public static <T> T toBean(String jsonStr, Class<T> beanClass) {
        return mapper.readValue(jsonStr, beanClass);
    }

    @SneakyThrows
    public static <T> List<T> toList(String jsonStr, TypeReference<List<T>> typeReference) {
        return mapper.readValue(jsonStr, typeReference);
    }

    @SneakyThrows
    public static <T> List<T> toList(String jsonStr, Class<T> beanClass) {
        JavaType javaType = mapper.getTypeFactory().constructParametricType(List.class, beanClass);
        return mapper.readValue(jsonStr, javaType);
    }

    @SneakyThrows
    public static <K, V> Map<K, V> toMap(String jsonStr, TypeReference<Map<K, V>> typeReference) {
        return mapper.readValue(jsonStr, typeReference);
    }

    @SneakyThrows
    public static <K, V> Map<K, V> toMap(String jsonStr, Class<K> keyClass, Class<V> valueClass) {
        JavaType javaType = mapper.getTypeFactory().constructParametricType(Map.class, keyClass, valueClass);
        return mapper.readValue(jsonStr, javaType);
    }

    @SneakyThrows
    public static Map<String, Object> toMap(String jsonStr) {
        return toMap(jsonStr, String.class, Object.class);
    }
}