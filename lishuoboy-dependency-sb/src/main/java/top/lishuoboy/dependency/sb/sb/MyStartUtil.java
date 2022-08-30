package top.lishuoboy.dependency.sb.sb;

import cn.hutool.core.net.NetUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;

/**
 * @author lishuoboy
 * @date 2022-8-30
 */
@Slf4j
public class MyStartUtil {

    /** 输出访问地址 */
    public static void printUrl(ApplicationContext context) {
        String ip = NetUtil.getLocalhostStr();
        String port = context.getEnvironment().getProperty("server.port", "8080");
        String path = context.getEnvironment().getProperty("server.servlet.context-path", "/");

        log.info(""
            + "\n===启动成功============"
            + "\n===远程访问地址 http://{}:{}{}"
            + "\n===本地访问地址 http://127.0.0.1:{}{}"
            + "\n===启动成功============\n", ip, port, path, port, path);
    }
}
