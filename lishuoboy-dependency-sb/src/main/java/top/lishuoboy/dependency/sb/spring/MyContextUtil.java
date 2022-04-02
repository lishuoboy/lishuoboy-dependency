package top.lishuoboy.dependency.sb.spring;

import org.springframework.context.ConfigurableApplicationContext;
import top.lishuoboy.dependency.base.str.MyStrPool;

import java.util.LinkedList;
import java.util.List;
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
     * @param printType
     */
    public static void printBeans(ConfigurableApplicationContext context, PrintType printType) {
        String[] beanNames = context.getBeanDefinitionNames();

        Map<String, String> beanMap = new TreeMap();    // 按全限定名排序
        for (int i = 0; i < beanNames.length; i++) {
            String beanName = beanNames[i];             // 全限定名
            String fullClassName = context.getBean(beanName).getClass().getName();  // 注入bean名字
            beanMap.put(fullClassName, beanName);
        }

        List<String> beanNameList = new LinkedList();           // 按全限定名排序
        for (String fullClassName : beanMap.keySet()) {
            beanNameList.add(beanMap.get(fullClassName));       // 注入bean名字
        }

        if (printType == null) {
            System.out.println(MyStrPool.LOG_PRE + beanMap);
        } else if (printType.equals(PrintType.NAME_AND_FULL_NAME)) {
            System.out.println(MyStrPool.LOG_PRE + beanMap);
        } else if (printType.equals(PrintType.NAME_ONLY)) {
            System.out.println(MyStrPool.LOG_PRE + beanNameList);
        } else {
            //
        }
    }

    public enum PrintType {
        NAME_ONLY,
        NAME_AND_FULL_NAME
    }
}