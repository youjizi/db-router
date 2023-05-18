package xyz.youjizi.middleware.router.annotation;

import java.lang.annotation.*;

/**
 * @description: 路由注解定义
 * @author： 有骥子
 * @date: 2023/5/18
 */

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface DBRouter {

    String key() default "";
}
