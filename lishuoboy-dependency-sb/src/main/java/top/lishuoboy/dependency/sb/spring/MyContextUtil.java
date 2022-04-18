package top.lishuoboy.dependency.sb.spring;

import org.springframework.context.ConfigurableApplicationContext;

import java.util.Map;
import java.util.TreeMap;

/**
 * String context 工具
 *
 * @author lishuoboy
 * @date 2022/4/2 17:29
 */
public class MyContextUtil {

    /**
     * 输出 spring 加载的 bean，按照所属包名字排序
     *
     * @param printPackage 是否输出包名
     */
    public static void printBeans(ConfigurableApplicationContext context, boolean printPackage) {
        String[] beanNames = context.getBeanDefinitionNames();

        Map<String, String> beanMap = new TreeMap();    // 按全限定名排序
        for (int i = 0; i < beanNames.length; i++) {
            String beanName = beanNames[i];             // 全限定名
            String fullClassName = context.getBean(beanName).getClass().getName();  // 注入bean名字
            beanMap.put(fullClassName, beanName);
        }

        for (Map.Entry<String, String> entry : beanMap.entrySet()) {
            if (printPackage) {
                System.out.println(entry);
            } else {
                System.out.println(entry.getValue());
            }
        }
    }

    /**
     * 输出 spring 加载的 bean，按照所属包名字排序，不输出包名
     */
    public static void printBeans(ConfigurableApplicationContext context) {
        printBeans(context, false);
    }
}