package top.lishuoboy.dependency.sb.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.StreamReadFeature;
import com.fasterxml.jackson.core.StreamWriteFeature;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.core.json.JsonWriteFeature;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.lishuoboy.dependency.base.json.HuJsonUtil;

import java.util.List;
import java.util.Map;

/**
 * JSON 工具类
 *
 * @author lishuoboy
 * @date 2022-1-10
 */
public class JacksonUtil {
    private static final Logger log = LoggerFactory.getLogger(HuJsonUtil.class);

    // 默认的build，提供外部使用
    public static final JsonMapper.Builder defaultMapperBuilder = JsonMapper.builder()
        .addModules(new JavaTimeModule())                           // JavaTimeModule 支持 LocalDateTime、Instant
        .enable(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY)       // 启用 Bean按照key名称自然排序
        .enable(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS)     // 启用 Map 按照key名称自然排序
        .disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);         // 禁用空bean失败

    private static final JsonMapper mapper = defaultMapperBuilder.build();

    @SneakyThrows
    public static String toJsonStr(Object obj, JsonMapper mapper, boolean prettyFormat) {
        return prettyFormat ? mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj) : mapper.writeValueAsString(obj);
    }

    @SneakyThrows
    public static String toJsonStr(Object obj) {
        return toJsonStr(obj, mapper, false);
    }

    @SneakyThrows
    public static String toJsonPrettyStr(Object obj) {
        return toJsonStr(obj, mapper, true);
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

    public static void printDefaultFeature() {
        /**
         * true==AUTO_CLOSE_TARGET
         * true==AUTO_CLOSE_JSON_CONTENT
         * true==FLUSH_PASSED_TO_STREAM
         * true==QUOTE_FIELD_NAMES
         * true==QUOTE_NON_NUMERIC_NUMBERS
         * false==ESCAPE_NON_ASCII
         * false==WRITE_NUMBERS_AS_STRINGS
         * false==WRITE_BIGDECIMAL_AS_PLAIN
         * false==STRICT_DUPLICATE_DETECTION
         * false==IGNORE_UNKNOWN
         */
        for (JsonGenerator.Feature value : JsonGenerator.Feature.values()) {
            log.warn("{}==JsonGenerator.Feature.{}", value.enabledByDefault(), value);
        }
        /**
         * true==AUTO_CLOSE_SOURCE
         * false==ALLOW_COMMENTS
         * false==ALLOW_YAML_COMMENTS
         * false==ALLOW_UNQUOTED_FIELD_NAMES
         * false==ALLOW_SINGLE_QUOTES
         * false==ALLOW_UNQUOTED_CONTROL_CHARS
         * false==ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER
         * false==ALLOW_NUMERIC_LEADING_ZEROS
         * false==ALLOW_LEADING_DECIMAL_POINT_FOR_NUMBERS
         * false==ALLOW_NON_NUMERIC_NUMBERS
         * false==ALLOW_MISSING_VALUES
         * false==ALLOW_TRAILING_COMMA
         * false==STRICT_DUPLICATE_DETECTION
         * false==IGNORE_UNDEFINED
         * true==INCLUDE_SOURCE_IN_LOCATION
         */
        for (JsonParser.Feature value : JsonParser.Feature.values()) {
            log.warn("{}==JsonParser.Feature.{}", value.enabledByDefault(), value);
        }
        /**
         * true ==QUOTE_FIELD_NAMES        // 确定是否使用双引号引用 JSON 对象字段名称的功能，如 JSON 规范所指定的那样。添加了禁用引用的功能以支持通常不期望它们的用例，这通常在直接从 Javascript 使用时发生。
         * true ==WRITE_NAN_AS_STRINGS     // 确定“NaN”（“非数字”，即非实数）浮点/双精度值是否作为 JSON 字符串输出的功能。  //检查的值是Double.Nan,Double.POSITIVE_INFINITY和Double.NEGATIVE_INIFINTY（以及相关的Float值）。      //如果功能被禁用，这些数字仍然使用关联的文字值输出，导致不合格的输出。
         * false==WRITE_NUMBERS_AS_STRINGS // 强制将所有常规数字值写入 JSON 字符串而不是 JSON 数字（即用双引号括起来）的功能
         * false==ESCAPE_NON_ASCII         // 指定超过 7 位 ASCII 范围（即 128 及以上的代码点）的所有字符都需要使用反斜杠转义输出的功能。
         */
        for (JsonWriteFeature value : JsonWriteFeature.values()) {
            log.warn("{}==JsonWriteFeature.{}", value.enabledByDefault(), value);
        }
        /**
         * false==ALLOW_JAVA_COMMENTS                       // 启用该功能允许识别和处理“C 注释”（/ * ... * /）和“C++注释”（// ....）
         * false==ALLOW_YAML_COMMENTS                       // 对于 JSON：启用该功能允许识别和处理“散列评论”（# ....）
         * false==ALLOW_SINGLE_QUOTES                       // 确定解析器是否允许使用单引号（撇号、字符“'”）来引用 JSON 字符串（名称和字符串值）的功能。如果是这样，这是对其他可接受的标记的补充。
         * false==ALLOW_UNQUOTED_FIELD_NAMES                // 确定解析器是否允许使用不带引号的字段名称的功能（例如，Javascript 允许，但 JSON 规范不允许）。
         * false==ALLOW_UNESCAPED_CONTROL_CHARS             // 确定解析器是否允许 JSON 字符串包含未转义的控制字符（值小于 32 的 ASCII 字符，包括制表符和换行符）的功能。 如果设置了 feature false，遇到这样的字符就会抛出异常。
         * false==ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER    // 可以启用以接受使用反斜杠引用机制对所有字符进行引用的功能：如果未启用，则只能转义 JSON 规范明确列出的字符（有关这些字符的小列表，请参阅 JSON 规范）
         * false==ALLOW_LEADING_ZEROS_FOR_NUMBERS           // 确定解析器是否允许 JSON 整数以附加（可忽略）零开头的功能（例如：000001）。 如果启用，则不会引发异常，并且会默默忽略多余的零（并且不包含在通过JsonParser.getText().
         * false==ALLOW_LEADING_DECIMAL_POINT_FOR_NUMBERS   // 确定解析器是否允许 JSON 十进制数字以小数点开头的功能（如：）.123。 如果启用，则不会引发异常，并且解析该数字就好像存在前导 0。
         * false==ALLOW_NON_NUMERIC_NUMBERS                 // 允许解析器将一组“非数字”(NaN) 标记识别为合法浮点数值的功能（类似于许多其他数据格式和编程语言源代码允许它）。 特定子集包含XML Schema（参见第 3.2.4.1 节，词法表示）允许的值（标记是引用的内容，不包括引号）：“INF”（正无穷大），以及“Infinity”的别名;“-INF”（负无穷大），别名“-Infinity”;“NaN”（对于其他非数字，例如除以零的结果）
         * false==ALLOW_MISSING_VALUES                      // 允许将“缺失”值（两个逗号之间的空格，在 JSON 数组上下文中）转换为null值，而不是异常
         * false==ALLOW_TRAILING_COMMA                      //  确定是否JsonParser允许在最终值（在数组中）或成员（在对象中）之后的单个尾随逗号的功能。这些逗号将被忽略。   例如，启用此功能时，[true,true,]` is equivalent to `[true, true]等效{"a": true,}于{"a": true}.
         */
        for (JsonReadFeature value : JsonReadFeature.values()) {
            log.warn("{}==JsonReadFeature.{}", value.enabledByDefault(), value);
        }
        /**
         * false==WRAP_ROOT_VALUE
         * false==INDENT_OUTPUT                         // 缩进输出
         * true==FAIL_ON_EMPTY_BEANS                    // 空bean失败
         * true==FAIL_ON_SELF_REFERENCES                // 自引用失败
         * true==WRAP_EXCEPTIONS                        // 包装异常
         * true==FAIL_ON_UNWRAPPED_TYPE_IDENTIFIERS     // 未包装的类型标识符失败
         * false==WRITE_SELF_REFERENCES_AS_NULL         // 将自引用写为 NULL
         * false==CLOSE_CLOSEABLE                       // CLOSE_CLOSEABLE
         * true==FLUSH_AFTER_WRITE_VALUE                // 写入值后刷新
         * true==WRITE_DATES_AS_TIMESTAMPS              // 将日期写为时间戳
         * false==WRITE_DATE_KEYS_AS_TIMESTAMPS         // 将日期键写为时间戳
         * false==WRITE_DATES_WITH_ZONE_ID              // 使用区域 ID 写入日期
         * true==WRITE_DURATIONS_AS_TIMESTAMPS          // 将持续时间写为时间戳
         * false==WRITE_CHAR_ARRAYS_AS_JSON_ARRAYS      // 将字符数组写入 JSON 数组
         * false==WRITE_ENUMS_USING_TO_STRING           // 使用字符串写入枚举
         * false==WRITE_ENUMS_USING_INDEX               // 使用索引编写枚举
         * false==WRITE_ENUM_KEYS_USING_INDEX           // 使用索引写入枚举键
         * true==WRITE_NULL_MAP_VALUES                  // 写空映射值
         * true==WRITE_EMPTY_JSON_ARRAYS                // 写空 JSON 数组
         * false==WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED    // 写展开的单元素数组
         * false==WRITE_BIGDECIMAL_AS_PLAIN             // 将 BIGDECIMAL 写入 PLAIN
         * true==WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS   // 将日期时间戳写为纳秒
         * false==ORDER_MAP_ENTRIES_BY_KEYS             // Map 按key排序
         * true==EAGER_SERIALIZER_FETCH                 // 渴望序列化器获取
         * false==USE_EQUALITY_FOR_OBJECT_ID            // 对对象 ID 使用相等性
         */
        for (SerializationFeature value : SerializationFeature.values()) {
            log.warn("{}==SerializationFeature.{}", value.enabledByDefault(), value);
        }
        /**
         * false==USE_BIG_DECIMAL_FOR_FLOATS
         * false==USE_BIG_INTEGER_FOR_INTS
         * false==USE_LONG_FOR_INTS
         * false==USE_JAVA_ARRAY_FOR_JSON_ARRAY
         * true==FAIL_ON_UNKNOWN_PROPERTIES
         * false==FAIL_ON_NULL_FOR_PRIMITIVES
         * false==FAIL_ON_NUMBERS_FOR_ENUMS
         * true==FAIL_ON_INVALID_SUBTYPE
         * false==FAIL_ON_READING_DUP_TREE_KEY
         * false==FAIL_ON_IGNORED_PROPERTIES
         * true==FAIL_ON_UNRESOLVED_OBJECT_IDS
         * false==FAIL_ON_MISSING_CREATOR_PROPERTIES
         * false==FAIL_ON_NULL_CREATOR_PROPERTIES
         * true==FAIL_ON_MISSING_EXTERNAL_TYPE_ID_PROPERTY
         * false==FAIL_ON_TRAILING_TOKENS
         * true==WRAP_EXCEPTIONS
         * false==ACCEPT_SINGLE_VALUE_AS_ARRAY
         * false==UNWRAP_SINGLE_VALUE_ARRAYS
         * false==UNWRAP_ROOT_VALUE
         * false==ACCEPT_EMPTY_STRING_AS_NULL_OBJECT
         * false==ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT
         * true==ACCEPT_FLOAT_AS_INT
         * false==READ_ENUMS_USING_TO_STRING
         * false==READ_UNKNOWN_ENUM_VALUES_AS_NULL
         * false==READ_UNKNOWN_ENUM_VALUES_USING_DEFAULT_VALUE
         * true==READ_DATE_TIMESTAMPS_AS_NANOSECONDS
         * true==ADJUST_DATES_TO_CONTEXT_TIME_ZONE
         * true==EAGER_DESERIALIZER_FETCH
         */
        for (DeserializationFeature value : DeserializationFeature.values()) {
            log.warn("{}==DeserializationFeature.{}", value.enabledByDefault(), value);
        }
        /**
         * true==AUTO_CLOSE_TARGET
         * true==AUTO_CLOSE_CONTENT
         * true==FLUSH_PASSED_TO_STREAM
         * false==WRITE_BIGDECIMAL_AS_PLAIN
         * false==STRICT_DUPLICATE_DETECTION
         * false==IGNORE_UNKNOWN
         */
        for (StreamWriteFeature value : StreamWriteFeature.values()) {
            log.warn("{}==StreamWriteFeature.{}", value.enabledByDefault(), value);
        }
        /**
         * true==AUTO_CLOSE_SOURCE
         * false==STRICT_DUPLICATE_DETECTION
         * false==IGNORE_UNDEFINED
         * true==INCLUDE_SOURCE_IN_LOCATION
         */
        for (StreamReadFeature value : StreamReadFeature.values()) {  // 基本全用的 JsonParser.Feature
            log.warn("{}==StreamReadFeature.{}", value.enabledByDefault(), value);
        }
        /**
         * true==INCLUDE_SOURCE_IN_LOCATION
         * true==USE_ANNOTATIONS
         * true==USE_GETTERS_AS_SETTERS
         * false==PROPAGATE_TRANSIENT_MARKER
         * true==AUTO_DETECT_CREATORS
         * true==AUTO_DETECT_FIELDS
         * true==AUTO_DETECT_GETTERS
         * true==AUTO_DETECT_IS_GETTERS
         * true==AUTO_DETECT_SETTERS
         * false==REQUIRE_SETTERS_FOR_GETTERS
         * true==ALLOW_FINAL_FIELDS_AS_MUTATORS
         * true==INFER_PROPERTY_MUTATORS
         * true==INFER_CREATOR_FROM_CONSTRUCTOR_PROPERTIES
         * true==CAN_OVERRIDE_ACCESS_MODIFIERS
         * true==OVERRIDE_PUBLIC_ACCESS_MODIFIERS
         * false==USE_STATIC_TYPING
         * false==USE_BASE_TYPE_AS_DEFAULT_IMPL
         * true==DEFAULT_VIEW_INCLUSION
         * false==SORT_PROPERTIES_ALPHABETICALLY
         * false==ACCEPT_CASE_INSENSITIVE_PROPERTIES
         * false==ACCEPT_CASE_INSENSITIVE_ENUMS
         * false==ACCEPT_CASE_INSENSITIVE_VALUES
         * false==USE_WRAPPER_NAME_AS_PROPERTY_NAME
         * false==USE_STD_BEAN_NAMING
         * false==ALLOW_EXPLICIT_PROPERTY_RENAMING
         * true==ALLOW_COERCION_OF_SCALARS
         * true==IGNORE_DUPLICATE_MODULE_REGISTRATIONS
         * true==IGNORE_MERGE_FOR_UNMERGEABLE
         */
        for (MapperFeature value : MapperFeature.values()) {
            log.warn("{}==MapperFeature.{}", value.enabledByDefault(), value);
        }
    }

}