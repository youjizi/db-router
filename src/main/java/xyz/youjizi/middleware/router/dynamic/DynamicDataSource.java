package xyz.youjizi.middleware.router.dynamic;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import xyz.youjizi.middleware.router.DBContextHolder;

/**
 * @description: 动态数据源，获取DBContextHolder中保存的路由结果
 * @author： 有骥子
 * @date: 2023/5/18
 */
public class DynamicDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        return "db" + DBContextHolder.getDBKey();
    }
}
