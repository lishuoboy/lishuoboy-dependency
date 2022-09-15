package top.lishuoboy.dependency.base.system;

import org.junit.Test;

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
    //    0  空样式
    //    1  粗体
    //    4  下划线
    //    7  反色

    // 二、字体颜色========================
    public static final String FONT_BLACK = "\33[30m";
    public static final String FONT_RED = "\33[31m";
    public static final String FONT_GREEN = "\33[32m";
    public static final String FONT_YELLOW = "\33[33m";
    public static final String FONT_BLUE = "\33[34m";
    /** 紫色 */
    public static final String FONT_PURPLE = "\33[35m";
    /** 青色 */
    public static final String FONT_CYAN = "\33[36m";
    public static final String FONT_WHITE = "\33[37m";

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

    // 四、综合demo========================
    public static final String ALL = "\33[37;40;7m";

    @Test
    public void test() {
        System.out.println(FONT_GREEN + 11111 + RESET);
        System.out.println(BG_GREEN + 22222 + RESET);
        System.out.println(ALL + 33333 + RESET);
    }

    /**
     * 彩色打印
     *
     * @param color   颜色
     * @param content 内容
     */
    public static void colorPrint(String color, String content) {
        System.out.println(color + content + RESET);
    }
}
