package xyz.youjizi.middleware.router;

/**
 * @description:
 * @author： 有骥子
 * @date: 2023/5/19
 */
public class DBRouterBase {
    private String tbIdx;

    public String getTbIdx() {
        return DBContextHolder.getTBKey();
    }
}
