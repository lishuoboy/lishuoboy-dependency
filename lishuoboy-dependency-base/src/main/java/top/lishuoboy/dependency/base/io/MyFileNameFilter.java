package top.lishuoboy.dependency.base.io;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FilenameFilter;

/**
 * 文件名过滤器
 */
@Slf4j
public class MyFileNameFilter implements FilenameFilter {
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
     * 构造文件名过滤器
     *
     * @param refuseStrs         不包含的字符串,              全部拒绝 传 new String[]{""}, 全部接受 传 null 或 new String[]{}
     * @param containsAnyStrs    包含任意一个字符串,           全部接受 传 new String[]{""}, 全部拒绝 传 null 或 new String[]{}
     * @param containsAnySuffixs 包含任意一个后缀名，不要 “.” , 全部接受 传 new String[]{""}, 全部拒绝 传 null 或 new String[]{} 或 不传
     */
    public MyFileNameFilter(String[] refuseStrs, String[] containsAnyStrs, String... containsAnySuffixs) {
        this.refuseStrs = refuseStrs;
        this.containsAnyStrs = containsAnyStrs;
        this.containsAnySuffixs = containsAnySuffixs;
    }

    @Override
    public boolean accept(File dir, String name) {
        if (!StrUtil.containsAnyIgnoreCase(name, refuseStrs)
            && StrUtil.containsAnyIgnoreCase(name, containsAnyStrs)
            && StrUtil.equalsAnyIgnoreCase(FileUtil.getSuffix(name), containsAnySuffixs)
        ) {
            log.trace("符合条件\t{}", FileUtil.file(dir, name));
            return true;
        } else {
            log.trace("不符合条件\t{}", FileUtil.file(dir, name));
            return false;
        }
    }

}