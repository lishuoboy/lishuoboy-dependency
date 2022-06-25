package top.lishuoboy.dependency.base.db;

import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.db.DbUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lishuoboy
 * @date 2022.06.18 18:11
 */
@Slf4j
public class MyDbUtil {
    /**
     * 重置db数据
     *
     * @param dataSource   数据源
     * @param resourceUtf8 sql 文件位置
     */
    @SneakyThrows
    public static void resetData(DataSource dataSource, String resourceUtf8) {
        log.info("重置DB数据 start");
        String[] sqlArr = StrUtil.trimToEmpty(ResourceUtil.readUtf8Str(resourceUtf8)).split(";");  // sql文件最后一个分号后面不能有空白
        int[] affectedCountArr = DbUtil.use(dataSource).executeBatch(sqlArr);
        log.info("重置DB数据 end。受影响记录数=={}", affectedCountArr);
    }

    /**
     * 重置db数据。非批量。适用于 ShardingSphere 这类分表工具。
     * ShardingSphere 的数据源重新了 java.sql.Statement.addBatch( String sql )方法，直接抛异常。
     * 详见 ShardingSphere 的类 AbstractUnsupportedOperationStatement
     *
     * @param dataSource   数据源
     * @param resourceUtf8 sql 文件位置
     */
    @SneakyThrows
    public static void resetDataNotBatch(DataSource dataSource, String resourceUtf8) {
        log.info("重置DB数据 start");
        String[] sqlArr = StrUtil.trimToEmpty(ResourceUtil.readUtf8Str(resourceUtf8)).split(";");  // sql文件最后一个分号后面不能有空白
        List affectedCountList = new ArrayList();
        for (String sql : sqlArr) {
            int affectedCount = DbUtil.use(dataSource).execute(sql);
            affectedCountList.add(affectedCount);
        }
        log.info("重置DB数据 end。受影响记录数=={}", affectedCountList);
    }
}