package xyz.youjizi.middleware.test;


import xyz.youjizi.middleware.router.annotation.DBRouter;

/**
 * 博客：https://bugstack.cn - 沉淀、分享、成长，让自己和他人都能有所收获！
 * 公众号：bugstack虫洞栈
 * Create by 小傅哥(fustack)
 */

public interface IUserDao {

    @DBRouter(key = "user123Id")
    void insertUser(String req);

}
