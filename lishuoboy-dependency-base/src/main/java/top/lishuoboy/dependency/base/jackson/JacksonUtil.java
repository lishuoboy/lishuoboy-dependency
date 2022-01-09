package top.lishuoboy.dependency.base.jackson;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

/**
 * Jackson 工具类
 *
 * @author lishuoboy
 * @date 2022-1-10
 */
@Slf4j
public class JacksonUtil {

    private static final ObjectMapper mapper = new ObjectMapper();

    @SneakyThrows
    public static String bean2Str(Object obj) {
        return mapper.writeValueAsString(obj);
    }

    @SneakyThrows
    public static String bean2PrettyStr(Object obj) {
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
    }

    @SneakyThrows
    public static <T> T str2Bean(String jsonStr, Class<T> beanType) {
        T result = mapper.readValue(jsonStr, beanType);
        return result;
    }

    @SneakyThrows
    public static <T> List<T> str2List(String jsonStr, Class<T> beanType) {
        JavaType javaType = mapper.getTypeFactory().constructParametricType(List.class, beanType);
        List<T> resultList = mapper.readValue(jsonStr, javaType);
        return resultList;
    }

    @SneakyThrows
    public static <K, V> Map<K, V> str2Map(String jsonStr, Class<K> keyType, Class<V> valueType) {
        JavaType javaType = mapper.getTypeFactory().constructMapType(Map.class, keyType, valueType);
        Map<K, V> resultMap = mapper.readValue(jsonStr, javaType);
        return resultMap;
    }


}