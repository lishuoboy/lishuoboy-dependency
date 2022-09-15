package top.lishuoboy.dependency.base.io;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileFilter;

/**
 * 文件过滤器
 */
@Slf4j
public class MyFileFilter implements FileFilter {
    /**
     * 不包含的字符串,              全部拒绝 传 new String[]{""}, 全部接受 传 null 或 new String[]{}
     */
    private String[] refuseStrs;
    /**
     * 包含任意一个字符串,           全部接受 传 new String[]{""}, 全部拒绝 传 null 或 new String[]{}
     */
    private String[] containsAnyStrs;
    /**
     * 包含任意一个后缀名，不要 “.” , 全部接受 传 new String[]{""}, 全部拒绝 传 null 或 new String[]{} 或 不传
     */
    private String[] containsAnySuffixs;

    /**
     * 构造文件过滤器
     *
     * @param refuseStrs         不包含的字符串,              全部拒绝 传 new String[]{""}, 全部接受 传 null 或 new String[]{}
     * @param containsAnyStrs    包含任意一个字符串,           全部接受 传 new String[]{""}, 全部拒绝 传 null 或 new String[]{}
     * @param containsAnySuffixs 等于任意一个后缀名，不要 “.” , 全部接受 传 new String[]{""}, 全部拒绝 传 null 或 new String[]{} 或 不传
     */
    public MyFileFilter(String[] refuseStrs, String[] containsAnyStrs, String... containsAnySuffixs) {
        this.refuseStrs = refuseStrs;
        this.containsAnyStrs = containsAnyStrs;
        this.containsAnySuffixs = containsAnySuffixs;
    }

    @Override
    public boolean accept(File pathname) {
        String dir = pathname.getParent();
        String name = pathname.getName();
        if (!StrUtil.containsAnyIgnoreCase(name, refuseStrs)
            && StrUtil.containsAnyIgnoreCase(name, containsAnyStrs)
            && StrUtil.containsAnyIgnoreCase(FileUtil.getSuffix(name), containsAnySuffixs)
        ) {
            log.trace("符合条件\t{}", FileUtil.file(dir, name));
            return true;
        } else {
            log.trace("不符合条件\t{}", FileUtil.file(dir, name));
            return false;
        }
    }

}