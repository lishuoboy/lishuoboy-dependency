package top.lishuoboy.dependency.sb.sb;

import cn.hutool.core.date.BetweenFormatter;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Console;
import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.RuntimeUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import top.lishuoboy.dependency.base.system.MyConsoleUtil;

/**
 * SpringBoot工具
 *
 * @author lishuoboy
 * @date 2022-8-30
 */
@Slf4j
public class MySbUtil {

    /** 启动 SB 并 输出 “访问地址”、“启动用时” */
    public static void run(Class clazz, String[] args, boolean openUrl) {
        long startDate = DateUtil.current();

        ApplicationContext context = SpringApplication.run(clazz, args);

        String duration = DateUtil.formatBetween(DateUtil.current() - startDate, BetweenFormatter.Level.MILLISECOND);
        String ip = NetUtil.getLocalhostStr();
        String port = context.getEnvironment().getProperty("server.port", "8080");
        String path = context.getEnvironment().getProperty("server.servlet.context-path", "/");
        String remoteUrl = StrUtil.format("http://{}:{}{}", ip, port, path);
        String localUrl = StrUtil.format("http://{}:{}{}", "localhost", port, path);

//        log.info(""
//                + "\n====================启动成功===================="
//                + "\n\t启动用时:\t {}"
//                + "\n\t远程地址:\t {}"
//                + "\n\t本地地址:\t {}"
//                + "\n====================启动成功====================\n"
//            , duration, remoteUrl, localUrl);
        MyConsoleUtil.colorPrint(MyConsoleUtil.FONT_GREEN, "====================启动成功====================");
        Console.log("\t启动用时:\t {}", duration);
        Console.log("\t远程地址:\t {}", remoteUrl);
        Console.log("\t本地地址:\t {}", localUrl);
        MyConsoleUtil.colorPrint(MyConsoleUtil.FONT_GREEN, "====================启动成功====================");

        if (openUrl) {
            RuntimeUtil.exec("cmd /c start " + remoteUrl);  // 打开网址
        }
    }

    /** 启动 SB 并 输出 “访问地址”、“启动用时”。不打开网址 */
    public static void run(Class clazz, String[] args) {
        run(clazz, args, false);
    }
}
