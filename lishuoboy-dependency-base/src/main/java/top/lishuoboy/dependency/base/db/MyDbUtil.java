package top.lishuoboy.dependency.base.db;

import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.db.DbUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;

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
        String[] sqlArr = ResourceUtil.readUtf8Str(resourceUtf8).split(";");
        int[] AffectedCountArr = DbUtil.use(dataSource).executeBatch(sqlArr);
        log.info("重置DB数据 end。受影响记录数=={}", AffectedCountArr);
    }
}