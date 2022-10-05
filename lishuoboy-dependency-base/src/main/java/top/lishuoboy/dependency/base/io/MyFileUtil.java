package top.lishuoboy.dependency.base.io;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.File;

@Slf4j
public class MyFileUtil {

    public static final String DATE_FORMAT = "yyyyMMdd-HHmmss";

    /**
     * 获取文件的修改时间。返回格式为 yyyyMMdd-HHmmss
     *
     * @return void
     *
     * @date 2015-7-25 下午7:30:47
     */
    public static String getLastModifiedDate(File imgFile) {
        return DateUtil.format(cn.hutool.core.io.FileUtil.lastModifiedTime(imgFile), DATE_FORMAT);
    }

}