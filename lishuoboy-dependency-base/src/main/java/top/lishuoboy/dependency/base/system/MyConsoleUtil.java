package top.lishuoboy.dependency.base.system;

import cn.hutool.core.lang.Console;
import org.junit.Test;

import java.util.List;

/**
 * 彩色日志输出打印
 *
 * @author lishuoboy
 * @date 2022-9-15
 */
public class MyConsoleUtil {
    /** 重置，每次结束都调用一下 */
    public static final String RESET = "\33[0m";   // 字符串的 16进制 \33  即 八进制 \33。 数值的二、八、十、十六进制表示 0b 0 1 0x

    // 一、样式========================
    /** 空样式 */
    public static final String STYLE_EMPTY = "\33[0m";
    /** 粗体 */
    public static final String STYLE_BOLD = "\33[1m";
    /** 斜体 */
    public static final String STYLE_ITALIC = "\33[3m";
    /** 下划线 */
    public static final String STYLE_UNDERLINE = "\33[4m";
    /** 反色 */
    public static final String STYLE_REVERSE_COLOR = "\33[7m";
    /** 删除线 */
    public static final String STYLE_STRIKETHROUGH = "\33[9m";

    // 二、字体颜色========================
    public static final String BLACK = "\33[30m";
    public static final String RED = "\33[31m";
    public static final String GREEN = "\33[32m";
    public static final String YELLOW = "\33[33m";
    public static final String BLUE = "\33[34m";
    /** 紫色 */
    public static final String PURPLE = "\33[35m";
    /** 青色 */
    public static final String CYAN = "\33[36m";
    public static final String WHITE = "\33[37m";

    // 更亮字体颜色========================
    public static final String BLACK2 = "\33[90m";
    public static final String RED2 = "\33[91m";
    public static final String GREEN2 = "\33[92m";
    public static final String YELLOW2 = "\33[93m";
    public static final String BLUE2 = "\33[94m";
    /** 紫色 */
    public static final String PURPLE2 = "\33[95m";
    /** 青色 */
    public static final String CYAN2 = "\33[96m";
    public static final String WHITE2 = "\33[97m";

    // 三、背景颜色========================
    public static final String BG_BLACK = "\33[40m";
    public static final String BG_RED = "\33[41m";
    public static final String BG_GREEN = "\33[42m";
    public static final String BG_YELLOW = "\33[43m";
    public static final String BG_BLUE = "\33[44m";
    /** 紫色 */
    public static final String BG_PURPLE = "\33[45m";
    /** 青色 */
    public static final String BG_CYAN = "\33[46m";
    public static final String BG_WHITE = "\33[47m";

    @Test
    public void test() {
        // 单用
        System.out.println(GREEN + 11111 + RESET);
        System.out.println(GREEN2 + 11111 + RESET);
        System.out.println(BG_GREEN + 22222 + RESET);
        System.out.println(STYLE_BOLD + 33333 + RESET);
        // 混用
        System.out.println(RED + BG_YELLOW + STYLE_BOLD + 44444 + RESET);     // 混用方式1
        System.out.println("\33[37;40;7m" + 55555 + RESET);                 // 混用方式2
    }

    /**
     * 彩色打印
     *
     * @param template 文本模板，被替换的部分用 {} 表示
     * @param styles   样式们
     * @param values   值
     */
    public static void colorPrint(String template, List<String> styles, Object... values) {
        for (String style : styles) {
            System.out.print(style);
        }
        Console.log(template + RESET, values);
    }
}
