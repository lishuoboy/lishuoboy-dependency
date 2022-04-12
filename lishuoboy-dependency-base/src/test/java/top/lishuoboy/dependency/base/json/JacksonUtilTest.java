package top.lishuoboy.dependency.base.json;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.map.MapUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

/**
 * @author lishuoboy
 * @date 2022/2/8 13:14
 */
@Slf4j
public class JacksonUtilTest {

    private User user = new User();

    @Test
    public void bean2Str() {
        log.info("{}", JacksonUtil.bean2Str(user));
        log.info("{}", JacksonUtil.bean2Str(ListUtil.list(false, user, user)));
        log.info("{}", JacksonUtil.bean2Str(MapUtil.builder("user1", user).put("user2", user).build()));
    }

    @Test
    public void bean2PrettyStr() {
        log.info("{}", JacksonUtil.bean2PrettyStr(user));
    }

    @Test
    public void str2Bean() {
        User user = JacksonUtil.str2Bean("{\"id\":1,\"name\":\"硕\"}", User.class);
        log.info("{}", user);
    }

    @Test
    public void str2List() {
        List<User> userList = JacksonUtil.str2List("[{\"id\":1,\"name\":\"硕\"},{\"id\":1,\"name\":\"硕\"}]", User.class);
        log.info("{}", userList);
    }

    @Test
    public void str2Map() {
        Map<String, Object> map = JacksonUtil.str2Map("{\"user1\":{\"id\":1,\"name\":\"硕\"},\"user2\":{\"id\":1,\"name\":\"硕\"}}");
        log.info("{}", map);
    }

    @Test
    public void str2MapGenerics() {
        Map<String, User> map = JacksonUtil.str2MapGenerics("{\"user1\":{\"id\":1,\"name\":\"硕\"},\"user2\":{\"id\":1,\"name\":\"硕\"}}", String.class, User.class);
        log.info("{}", map);
    }


    @Test
    public void bean2Bytes() {
        log.info("{}", JacksonUtil.bean2Bytes(user));
        log.info("{}", JacksonUtil.bean2Bytes(ListUtil.list(false, user, user)));
        log.info("{}", JacksonUtil.bean2Bytes(MapUtil.builder("user1", user).put("user2", user).build()));
    }

    @Test
    public void bytes2Bean() {
        User user = JacksonUtil.bytes2Bean("{\"id\":1,\"name\":\"硕\"}".getBytes(StandardCharsets.UTF_8), User.class);
        log.info("{}", user);
    }

}