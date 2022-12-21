package top.lishuoboy.dependency.base.clazz;

import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.StrUtil;
import lombok.SneakyThrows;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class MyClassUtil {

    /**
     * 统计项目的依赖jar们中的类名的长度个数。
     * 不包括本项目的非jar的类
     *
     * @param printLen 超过 printLen 才输出
     */
    public static void stat(int printLen) {
        URL[] dependencyJars = getDependencyJars();
        Set<String> classNameSet = getClassNameSet(dependencyJars);

        Map<Integer, Integer> map = new TreeMap<>();
        for (String fullName : classNameSet) {
            // 截取类名，不带包名
            String name = StrUtil.subAfter(fullName, ".", true);
//            name = StrUtil.subBefore(name, "$", false);

            // 放入统计的 map key为length，val为count
            int length = name.length();
            if (map.get(length) == null) {
                map.put(length, 1);
            } else {
                map.put(length, map.get(length) + 1);
            }

            if (length >= printLen) {
                System.out.println(fullName);
            }
        }

        for (Object o : map.entrySet()) {
            System.out.println(o);
        }

    }

    /** 获取项目依赖的jar们 */
    @SneakyThrows
    public static URL[] getDependencyJars() {
        URL[] urls;
        //获取Classpath
        if (MyClassUtil.class.getClassLoader() instanceof URLClassLoader) {
            urls = ((URLClassLoader) MyClassUtil.class.getClassLoader()).getURLs();
        } else {
            String[] split = System.getProperty("java.class.path").split(";");
            urls = new URL[split.length];
            for (int i = 0; i < split.length; i++) {
                urls[i] = new File(split[i]).toURI().toURL();
            }
        }
        return urls;
    }

    /** 获取jar包中的所有java类全限定名。使用Set去重 */
    @SneakyThrows
    private static Set<String> getClassNameSet(URL... urls) {
        Set<String> classNameSet = new HashSet<>();
        for (URL url : urls) {
            if (url.toURI().getScheme().equals("file")) {//判断Scheme是不是file
                File file = new File(url.toURI());
                if (!file.exists()) {
                    continue;//是存在
                }
                if (file.isDirectory()) {//如果是目录
                    Files.walkFileTree(file.toPath(), new SimpleFileVisitor<Path>() {//遍历所有文件
                        @Override
                        public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) throws IOException {
                            if (path.toFile().getName().endsWith(".class")) {//判断是否为class后缀
                                //去除路径截取包名
                                String substring = path.toFile().getAbsolutePath().substring(file.getAbsolutePath().length());
                                if (substring.startsWith(File.separator)) {
                                    substring = substring.substring(1);
                                }
                                substring = substring.substring(0, substring.length() - 6);//去掉后缀
                                classNameSet.add(substring.replace(File.separator, "."));//把路径替换为.
                            }
                            return super.visitFile(path, attrs);
                        }
                    });
                } else if (file.getName().endsWith(".jar")) {//如果是jar包
                    JarFile jarFile = new JarFile(file);
                    Enumeration<JarEntry> entries = jarFile.entries();
                    while (entries.hasMoreElements()) {//遍历所有文件
                        JarEntry jarEntry = entries.nextElement();
                        if (!jarEntry.getName().endsWith(".class")) {
                            continue;//判断后缀是否为class
                        }
                        String replace = jarEntry.getName().replace("/", ".");//把路径替换为.
                        classNameSet.add(replace.substring(0, replace.length() - 6));//去掉后缀
                    }
                }
            }
        }
        return classNameSet;
    }


    /**
     * 输出 类的格式化所有字段<br>
     * 用于 @JsonPropertyOrder({"code", "msg", "token", "data"})
     * 或 @ApiOperationSupport(ignoreParameters = {"userId", "idCardNo", "email", "password", "userName"})
     *
     * @param clazz
     * @param leftStr   字段左侧符号
     * @param rightStr  字段右侧符号
     * @param separator 字段分隔符
     *
     * @return 如 “id”，“name”，“email”， ……
     */
    public static String printFields(Class clazz, String leftStr, String rightStr, String separator) {
        Field[] declaredFields = ClassUtil.getDeclaredFields(clazz);
        List<String> strList = new ArrayList<>();
        for (Field declaredField : declaredFields) {
            strList.add(declaredField.getName());
        }
        String fields = strList.toString();
        fields = StrUtil.replace(fields, "[", leftStr);
        fields = StrUtil.replace(fields, "]", rightStr);
        fields = StrUtil.replace(fields, ", ", rightStr + separator + leftStr);
        System.out.println(fields);
        return fields;
    }

    /**
     * 输出 类的格式化所有字段
     * 用于 @JsonPropertyOrder({"code", "msg", "token", "data"})
     * 或 @ApiOperationSupport(ignoreParameters = {"userId", "idCardNo", "email", "password", "userName"})
     *
     * @return 如 "id","name","email",……
     */
    public static String printFields(Class clazz) {
        return printFields(clazz, "\"", "\"", ",");
    }


}