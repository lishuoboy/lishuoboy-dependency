package top.lishuoboy.dependency.sb.sb;

import cn.hutool.core.date.BetweenFormatter;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.net.NetUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

/**
 * SpringBoot工具
 *
 * @author lishuoboy
 * @date 2022-8-30
 */
@Slf4j
public class MySbUtil {

    /** 启动 SB 并 输出 “访问地址”、“启动用时” */
    public static void run(Class clazz, String[] args) {
        long startDate = DateUtil.current();

        ApplicationContext context = SpringApplication.run(clazz, args);

        String duration = DateUtil.formatBetween(DateUtil.current() - startDate, BetweenFormatter.Level.MILLISECOND);
        String ip = NetUtil.getLocalhostStr();
        String port = context.getEnvironment().getProperty("server.port", "8080");
        String path = context.getEnvironment().getProperty("server.servlet.context-path", "/");

        log.info(""
                + "\n==========启动成功=================================================="
                + "\n\t启动用时:\t {}"
                + "\n\t远程地址:\t http://{}:{}{}"
                + "\n\t本地地址:\t http://localhost:{}{}"
                + "\n==========启动成功==================================================\n"
            , duration, ip, port, path, port, path);
    }
}
