package top.lishuoboy.dependency.base.json;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.map.MapUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.List;
import java.util.Map;

/**
 * @author lishuoboy
 * @date 2022/2/8 13:14
 */
@Slf4j
public class FastjsonUtilTest {

    private User user = new User();

    @Test
    public void bean2Str() {
        log.info("{}", FastjsonUtil.bean2Str(user));
        log.info("{}", FastjsonUtil.bean2Str(ListUtil.list(false, user, user)));
        log.info("{}", FastjsonUtil.bean2Str(MapUtil.builder("user1", user).put("user2", user).build()));
    }

    @Test
    public void bean2PrettyStr() {
        log.info("{}", FastjsonUtil.bean2PrettyStr(user));
    }

    @Test
    public void str2Bean() {
        User user = FastjsonUtil.str2Bean("{\"id\":1,\"name\":\"硕\"}", User.class);
        log.info("{}", user);
    }

    @Test
    public void str2List() {
        List<User> userList = FastjsonUtil.str2List("[{\"id\":1,\"name\":\"硕\"},{\"$ref\":\"$[0]\"}]", User.class);
        log.info("{}", userList);
    }

    @Test
    public void str2Map() {
        // 不知道怎么支持User
        Map<String, Object> map = FastjsonUtil.str2Map("{\"user1\":{\"id\":1,\"name\":\"硕\"},\"user2\":{\"$ref\":\"$.user1\"}}");
        log.info("{}", map);
    }
}