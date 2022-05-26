package top.lishuoboy.dependency.sb.spring;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.io.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ConfigurableApplicationContext;
import top.lishuoboy.dependency.base.json.HuJsonUtil;
import top.lishuoboy.dependency.base.str.MyStrPool;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * String context 工具
 *
 * @author lishuoboy
 * @date 2022/4/2 17:29
 */
@Slf4j
public class MyContextUtil {

    private static File beansFile = new File("tmp/spring_beans.json");
    private static File beansNameFile = new File("tmp/spring_beansName.json");
    private static File beansNameDisjunctionFile = new File("tmp/spring_beansDisjunctionName.json");

    /**
     * 获取 spring 加载的 beans(全限定类名=注入IOC的bean的名字)，按照全限定类名排序
     * key  ：     全限定类名（包名.类名）
     * value：   注入IOC的 bean的名字
     *
     * @param beanNames 指定beanNames。不指定时获取context的全部bean
     */
    private static TreeMap<String, String> getBeans(ConfigurableApplicationContext context, List<String> beanNames) {
        beanNames = beanNames == null ? ListUtil.list(false, context.getBeanDefinitionNames()) : beanNames;
        TreeMap<String, String> beanMap = new TreeMap();    // 按全限定类名排序
        for (String beanName : beanNames) {
            String fullClassName = context.getBean(beanName).getClass().getName();  // 注入bean名字
            beanMap.put(fullClassName, beanName);
        }
        return beanMap;
    }

    /**
     * 输出 spring 加载的 beans(注入IOC的 bean的名字)，按照全限定类名排序
     */
    public static ArrayList getBeansName(ConfigurableApplicationContext context) {
        return new ArrayList(getBeans(context, null).values());
    }

    /**
     * 输出 spring 加载的 beans(全限定类名=注入IOC的bean的名字)，按照全限定类名排序
     * key  ：     全限定类名（包名.类名）
     * value：   注入IOC的 bean的名字
     *
     * @param printFullName 是否包含全限定类名
     */
    public static void printBeans(ConfigurableApplicationContext context, boolean printFullName) {
        TreeMap beanMap = getBeans(context, null);
        List<String> beanNameList = new ArrayList(beanMap.values());
        if (printFullName) {
            FileUtil.writeUtf8String(HuJsonUtil.toJsonPrettyStr(beanMap), beansFile);
            log.warn(MyStrPool.LOG_PRE + "输出目录=={}", beansFile.getAbsolutePath());
        } else {
            FileUtil.writeUtf8String(HuJsonUtil.toJsonPrettyStr(beanNameList), beansNameFile);
            log.warn(MyStrPool.LOG_PRE + "输出目录=={}", beansNameFile.getAbsolutePath());
        }
    }

    /**
     * 输出 spring 加载的 beans(注入IOC的bean的名字)，按照全限定类名排序
     */
    public static void printBeans(ConfigurableApplicationContext context) {
        printBeans(context, false);
    }


    /**
     * 输出 spring 加载的 beans的差集，按照全限定类名排序
     * 1.清除tmp目录
     * 2.调用 printBeansDisjunction()，添加或删除依赖，再次调用 printBeansDisjunction()
     */
    public static void printBeansDisjunction(ConfigurableApplicationContext context, boolean printFullName) {
        log.warn("不能在之前调用 MyContextUtil.printBeans(context);");
        if (!beansNameFile.exists()) {
            printBeans(context);
            return;
        }
        List<String> beansNameListOld = HuJsonUtil.toList(FileUtil.readUtf8String(beansNameFile), String.class);
        List beansNameListNew = getBeansName(context);
        List beansNameDisjunctionList = ListUtil.list(false, CollUtil.disjunction(beansNameListOld, beansNameListNew));

        if (printFullName) {
            FileUtil.writeUtf8String(HuJsonUtil.toJsonPrettyStr(getBeans(context, beansNameDisjunctionList)), beansNameDisjunctionFile);
        } else {
            FileUtil.writeUtf8String(HuJsonUtil.toJsonPrettyStr(beansNameDisjunctionList), beansNameDisjunctionFile);
        }
        log.warn(MyStrPool.LOG_PRE + "输出目录=={}", beansNameDisjunctionFile.getAbsolutePath());
    }

    /**
     * 输出 spring 加载的 beans的差集，按照全限定类名排序
     * 1.清除tmp目录
     * 2.调用 printBeansDisjunction()，添加依赖，再次调用 printBeansDisjunction()
     */
    public static void printBeansDisjunction(ConfigurableApplicationContext context) {
        printBeansDisjunction(context, false);
    }
}