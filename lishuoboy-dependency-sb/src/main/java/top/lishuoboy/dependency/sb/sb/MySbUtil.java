package top.lishuoboy.dependency.sb.sb;

import cn.hutool.core.date.BetweenFormatter;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.RuntimeUtil;
import cn.hutool.core.util.StrUtil;
import lombok.NonNull;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import top.lishuoboy.dependency.base.system.MyConsoleUtil;

/**
 * SpringBoot工具
 *
 * @author lishuoboy
 * @date 2022-8-30
 */
public class MySbUtil {

    /**
     * 启动 SB 并 输出 “访问地址”、“启动用时”
     *
     * @param path 拼接在url端口后面的路径
     */
    public static ConfigurableApplicationContext run(Class clazz, String[] args, @NonNull String path, boolean openUrl) {
        if (!path.startsWith("/")) {
            path = "/" + path;
        }

        long startDate = DateUtil.current();

        ConfigurableApplicationContext context = SpringApplication.run(clazz, args);

        String duration = DateUtil.formatBetween(DateUtil.current() - startDate, BetweenFormatter.Level.MILLISECOND);
        String ip = NetUtil.getLocalhostStr();
        String port = context.getEnvironment().getProperty("server.port", "8080");
        String contextPath = context.getEnvironment().getProperty("server.servlet.context-path", "");
        String remoteUrl = StrUtil.format("http://{}:{}{}{}", ip, port, contextPath, path);
        String localUrl = StrUtil.format("http://{}:{}{}{}", "localhost", port, contextPath, path);

        MyConsoleUtil.colorPrint("====================启动成功====================", MyConsoleUtil.WHITE2, MyConsoleUtil.BG_CYAN, MyConsoleUtil.STYLE_BOLD);
        MyConsoleUtil.colorPrint(StrUtil.format("\t启动用时" + MyConsoleUtil.RESET + ":\t {}", duration), MyConsoleUtil.STYLE_BOLD);
        MyConsoleUtil.colorPrint(StrUtil.format("\t启动用时" + MyConsoleUtil.RESET + ":\t {}", remoteUrl), MyConsoleUtil.STYLE_BOLD);
        MyConsoleUtil.colorPrint(StrUtil.format("\t本地地址" + MyConsoleUtil.RESET + ":\t {}", localUrl), MyConsoleUtil.STYLE_BOLD);
        MyConsoleUtil.colorPrint("====================启动成功====================", MyConsoleUtil.WHITE2, MyConsoleUtil.BG_CYAN, MyConsoleUtil.STYLE_BOLD);

        if (openUrl) {
            RuntimeUtil.exec("cmd /c start " + remoteUrl);  // 打开网址
        }
        return context;
    }

    /** 启动 SB 并 输出 “访问地址”、“启动用时”。不打开网址 */
    public static ConfigurableApplicationContext run(Class clazz, String[] args) {
        return run(clazz, args, "");
    }

    /**
     * 启动 SB 并 输出 “访问地址”、“启动用时”。不打开网址
     *
     * @param path 拼接在url端口后面的路径
     */
    public static ConfigurableApplicationContext run(Class clazz, String[] args, String path) {
        return run(clazz, args, path, false);
    }
}
