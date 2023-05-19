package xyz.youjizi.middleware.router;

/**
 * @description:
 * @author： 有骥子
 * @date: 2023/5/18
 */
public class DBRouterConfig {

    /** 分库数 **/
    private int dbCount;
    /** 分表数 **/
    private int tbbCount;

    public DBRouterConfig() {
    }

    public DBRouterConfig(int dbCount, int tbbCount) {
        this.dbCount = dbCount;
        this.tbbCount = tbbCount;
    }

    public int getDbCount() {
        return dbCount;
    }

    public void setDbCount(int dbCount) {
        this.dbCount = dbCount;
    }

    public int getTbbCount() {
        return tbbCount;
    }

    public void setTbbCount(int tbbCount) {
        this.tbbCount = tbbCount;
    }
}
