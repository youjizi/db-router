package xyz.youjizi.middleware.router;


import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import xyz.youjizi.middleware.router.annotation.DBRouter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @description:
 * @author： 有骥子
 * @date: 2023/5/19
 */
@Aspect
@Component("db-router-point")
public class DBRouterJoinPoint {

    private Logger logger = LoggerFactory.getLogger(DBRouterJoinPoint.class);

    @Autowired
    private DBRouterConfig dbRouterConfig;

    @Pointcut("@annotation(xyz.youjizi.middleware.router.annotation.DBRouter)")
    public void aopPoint() {}

    @Around("aopPoint() && @annotation(dbRouter)")
    public Object doRouter(ProceedingJoinPoint jp, DBRouter dbRouter) throws Throwable {
        String dbKey = dbRouter.key();
        if (StringUtils.isBlank(dbKey)) {
            throw new RuntimeException("annotation DBRouter key is null");
        }
        // 计算路由
        String dbKeyAttr = getAttrValue(dbKey, jp.getArgs());
        int size = dbRouterConfig.getDbCount() * dbRouterConfig.getTbbCount();
        // 扰动函数
        int idx = (size - 1) & (dbKeyAttr.hashCode() ^ (dbKeyAttr.hashCode() >>> 16));
        // 库表索引
        int dbIdx = idx / dbRouterConfig.getDbCount() +1;
        int tbIdx = idx - dbRouterConfig.getTbbCount() * (dbIdx - 1);
        // 设置到 ThreadLocal
        DBContextHolder.setDBKey(String.format("%02d",dbIdx));
        DBContextHolder.setTBKey(String.format("%02d",tbIdx));
        logger.info("数据库路由 method:{} dbIdx:{} tdIdx: {}", getMethod(jp).getName(), dbIdx, tbIdx);
        // 返回结果
        try {
            return jp.proceed();
        } finally {
            DBContextHolder.clearDBKey();
            DBContextHolder.clearTBKey();
        }

    }

    private Method getMethod(ProceedingJoinPoint jp) throws NoSuchMethodException {
        Signature signature = jp.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        return jp.getTarget().getClass().getMethod(methodSignature.getName(), methodSignature.getParameterTypes());
    }


    private String getAttrValue(String attr, Object[] args) {
        String filedValue = null;
        for (Object arg : args) {

            try {
                if (StringUtils.isNotBlank(filedValue)) {
                    break;
                }
                filedValue = BeanUtils.getProperty(arg, attr);
            } catch (Exception e) {
             logger.error("获取路由属性值失败 attr: {}", attr, e);
            }
        }
        return filedValue;
    }

}
