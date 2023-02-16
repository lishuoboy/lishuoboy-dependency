package top.lishuoboy.dependency.sb.sb;

import cn.hutool.core.date.BetweenFormatter;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.RuntimeUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import lombok.NonNull;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import top.lishuoboy.dependency.base.system.MyConsoleUtil;

import java.util.LinkedHashSet;

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
        String template = "http://{}:{}{}{}";
        String port = context.getEnvironment().getProperty("server.port", "8080");
        String contextPath = context.getEnvironment().getProperty("server.servlet.context-path", "");
        String localhostUrl = StrUtil.format(template, "localhost", port, contextPath, path);       // localhost
        String localIpV4Url = StrUtil.format(template, "127.0.0.1", port, contextPath, path);       // 本地 IpV4
        String intranetIpV4Url = StrUtil.format(template, getIntranetIpV4(), port, contextPath, path);      // 内网 IPv4
        String localIpV6Url = StrUtil.format(template, "[::1]", port, contextPath, path);           // 本地 IpV6, 完整写法为 [0:0:0:0:0:0:0:1]
        String intranetIpV6Url = StrUtil.format(template, getIntranetIpV6(), port, contextPath, path);      // 内网 IPv6
        String internetIpV4Url = StrUtil.format(template, getInternetIpV4(), port, contextPath, path);      // 外网 IPv4

        MyConsoleUtil.colorPrint("====================启动成功====================", MyConsoleUtil.WHITE2, MyConsoleUtil.BG_CYAN, MyConsoleUtil.STYLE_BOLD);
        MyConsoleUtil.colorPrint(StrUtil.format("\t启动用时" + MyConsoleUtil.RESET + ":\t {}", duration), MyConsoleUtil.STYLE_BOLD);
        MyConsoleUtil.colorPrint(StrUtil.format("\t本地local" + MyConsoleUtil.RESET + ":\t {}", localhostUrl), MyConsoleUtil.STYLE_BOLD);
//        MyConsoleUtil.colorPrint(StrUtil.format("\t本地IPv4" + MyConsoleUtil.RESET + ":\t {}", localIpV4Url), MyConsoleUtil.STYLE_BOLD);
        MyConsoleUtil.colorPrint(StrUtil.format("\t内网IPv4" + MyConsoleUtil.RESET + ":\t {}", intranetIpV4Url), MyConsoleUtil.STYLE_BOLD);
//        MyConsoleUtil.colorPrint(StrUtil.format("\t本地IPv6" + MyConsoleUtil.RESET + ":\t {}", localIpV6Url), MyConsoleUtil.STYLE_BOLD);
//        MyConsoleUtil.colorPrint(StrUtil.format("\t内网IPv6" + MyConsoleUtil.RESET + ":\t {}", intranetIpV6Url), MyConsoleUtil.STYLE_BOLD);
        MyConsoleUtil.colorPrint(StrUtil.format("\t外网IPv4" + MyConsoleUtil.RESET + ":\t {}", internetIpV4Url), MyConsoleUtil.STYLE_BOLD);
        MyConsoleUtil.colorPrint("====================启动成功====================", MyConsoleUtil.WHITE2, MyConsoleUtil.BG_CYAN, MyConsoleUtil.STYLE_BOLD);

        if (openUrl) {
            RuntimeUtil.exec("cmd /c start " + localhostUrl);  // 打开网址
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

    /** 获取内网IPv4 */
    private static String getIntranetIpV4() {
        LinkedHashSet<String> ipv4s = NetUtil.localIpv4s();
        for (String ipv4 : ipv4s) {
            if (!ipv4.contains("127.0.0.1")) {
                return ipv4;
            }
        }
        return "";
    }

    /** 获取内网IPv6 */
    private static String getIntranetIpV6() {
        LinkedHashSet<String> ipv6s = NetUtil.localIpv6s();
        for (String ipv6 : ipv6s) {
            if (!ipv6.contains("0:0:0:0:0:0:0:1") && !ipv6.contains("::1")) {
                return "[" + StrUtil.subBefore(ipv6, "%", false) + "]";
            }
        }
        return "";
    }

    /** 获取外网IPv4 */
    private static String getInternetIpV4() {
        return HttpUtil.get("https://ifconfig.me/ip");
    }
}
