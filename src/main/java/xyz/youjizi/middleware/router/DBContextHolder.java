package xyz.youjizi.middleware.router;

/**
 * @description: 本地线程设置路由结果
 * @author： 有骥子
 * @date: 2023/5/18
 */
public class DBContextHolder {

    private static final ThreadLocal<String> dbKey = new ThreadLocal<String>();
    private static final ThreadLocal<String> tbKey = new ThreadLocal<String>();

    public static void setDBKey(String dbKeyIdx) {
        dbKey.set(dbKeyIdx);
    }
    public static String getDBKey() {
       return dbKey.get();
    }

    public static void setTBKey(String tbKeyIdx) {
        tbKey.set(tbKeyIdx);
    }
    public static String getTBKey() {
        return tbKey.get();
    }

    public static void clearDBKey() {
        dbKey.remove();
    }

    public static void clearTBKey() {
        tbKey.remove();
    }
}
