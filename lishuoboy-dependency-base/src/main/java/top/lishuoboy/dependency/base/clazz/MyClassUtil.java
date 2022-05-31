package top.lishuoboy.dependency.base.clazz;

import cn.hutool.core.util.StrUtil;
import lombok.SneakyThrows;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
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
     * 统计类的长度个数
     *
     * @param printLen 超过 printLen 才输出
     */
    public static void stat(int printLen) {
        Set<String> classSet = getClassSet();

        Map<Integer, Integer> map = new TreeMap<>();
        for (String fullName : classSet) {
            // 截取类名，不带包名
            String name = StrUtil.subAfter(fullName, ".", true);
            name = StrUtil.subBefore(name, "$", false);

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

    /** 获取所有类的全限定名 */
    @SneakyThrows
    public static Set<String> getClassSet() {
        List<URL> urls = new ArrayList<>();
        //获取Classpath
        if (MyClassUtil.class.getClassLoader() instanceof URLClassLoader) {
            Collections.addAll(urls, ((URLClassLoader) MyClassUtil.class.getClassLoader()).getURLs());
        } else {
            for (String s : System.getProperty("java.class.path").split(";")) {
                urls.add(new File(s).toURI().toURL());
            }
        }

        return walkAllClasses(urls);
    }

    /** 遍历所有类 */
    private static Set<String> walkAllClasses(List<URL> urls) throws URISyntaxException, IOException {
        Set<String> classes = new HashSet<>();
        for (URL url : urls) {
            if (url.toURI().getScheme().equals("file")) {//判断Scheme是不是file
                File file = new File(url.toURI());
                if (!file.exists()) continue;//是存在
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
                                classes.add(substring.replace(File.separator, "."));//把路径替换为.
                            }
                            return super.visitFile(path, attrs);
                        }
                    });
                } else if (file.getName().endsWith(".jar")) {//如果是jar包
                    JarFile jarFile = new JarFile(file);
                    Enumeration<JarEntry> entries = jarFile.entries();
                    while (entries.hasMoreElements()) {//遍历所有文件
                        JarEntry jarEntry = entries.nextElement();
                        if (!jarEntry.getName().endsWith(".class")) continue;//判断后缀是否为class
                        String replace = jarEntry.getName().replace("/", ".");//把路径替换为.
                        classes.add(replace.substring(0, replace.length() - 6));//去掉后缀
                    }
                }
            }
        }
        return classes;
    }
}