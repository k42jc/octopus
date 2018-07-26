package com.dafy.octopus.auth.aop;

import com.dafy.octopus.web.core.domain.Request;
import com.dafy.octopus.web.core.domain.ResponseCode;
import com.dafy.octopus.web.core.utils.CommonUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 禁止编辑管理员信息
 * Created by liaoxudong
 * Date:2018/5/16
 */

@Aspect
@Component
@Order(-2)// 先于记录日志拦截
public class DisableEditAdminAspect {
    // 管理员id固定为1
    private static final long ADMIN_ID = 1L;

    private static final Logger logger = LoggerFactory.getLogger(DisableEditAdmin.class);

    @Around("@annotation(disableEditAdmin)")
    public Object around(ProceedingJoinPoint pjp, DisableEditAdmin disableEditAdmin) throws Throwable{
        DisableEditAdmin.EditType editType = disableEditAdmin.value();
        Object o = pjp.getArgs()[0];
        handler(o,editType);
        return pjp.proceed();
    }

    /**
     * 禁止操作管理员
     * @param o
     * @param editType
     */
    private void handler(Object o,DisableEditAdmin.EditType editType) {
        if (o instanceof Long) {// 删除
            if (ADMIN_ID == (Long)o) {
                logger.warn("删除管理员信息，禁止操作");
                CommonUtils.throwException(ResponseCode.DISABLE_EDIT_ADMIN);
            }
        } else if (o instanceof Request) {// 编辑
            Request request = (Request) o;
            switch (editType) {
                case USER:
                    if (request.getLong("userId") == ADMIN_ID) {
                        logger.warn("编辑管理员信息，禁止操作");
                        CommonUtils.throwException(ResponseCode.DISABLE_EDIT_ADMIN);
                    }
                    break;
                case ROLE:
                    if (request.getLong("id") == ADMIN_ID) {
                        logger.warn("编辑管理员角色信息，禁止操作");
                        CommonUtils.throwException(ResponseCode.DISABLE_EDIT_ADMIN);
                    }
                    break;
            }
        }
    }
}
