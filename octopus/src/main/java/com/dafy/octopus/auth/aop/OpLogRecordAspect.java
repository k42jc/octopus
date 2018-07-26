package com.dafy.octopus.auth.aop;

import com.dafy.common.po.Response;
import com.dafy.common.thread.pool.ThreadPoolFactory;
import com.dafy.common.util.JsonUtils;
import com.dafy.octopus.web.core.aop.OpLogRecord;
import com.dafy.octopus.web.core.domain.OpLogType;
import com.dafy.octopus.web.core.domain.Request;
import com.dafy.octopus.web.core.dto.OpLog;
import com.dafy.octopus.web.core.dto.User;
import com.dafy.octopus.auth.service.IOpLogSevice;
import com.dafy.octopus.web.core.utils.SubjectUtils;
import com.dafy.octopus.web.core.utils.RequestUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 操作日志记录拦截实现
 *
 * 本来打算拦截Service层操作方法，但是会出现很多方法AOP失效的现象(并不是全部失效)
 * 所以改变主意拦截Controller方法，作为记录日志来说，拦截这一层更加合适
 * Created by liaoxudong
 * Date:2018/5/10
 */

@Aspect
@Component
@Order(-1)
public class OpLogRecordAspect {
    private static final Logger logger = LoggerFactory.getLogger(OpLogRecordAspect.class);

    @Autowired
    private IOpLogSevice opLogSevice;

    /**
     * 环绕增强，因为需要同时获取方法执行前后的状态
     * @param pjp
     * @param opLogRecord
     * @return
     * @throws Throwable
     */
    @Around("@annotation(opLogRecord)")
    public Object around(ProceedingJoinPoint pjp, OpLogRecord opLogRecord) throws Throwable{
        Object result = null;// 在返回后记录
        Throwable throwable = null;
        // 在执行前获取一次全局用户
        User user = SubjectUtils.getCurrentUser();
        try {
            result = pjp.proceed();
        } catch (Throwable t) {
            throwable = t;
        }
        addOpLog(opLogRecord, user, result,pjp.getArgs(),throwable);
        if (throwable != null) {// 如果拦截的方法出现了异常，还是得抛出去统一处理
            throw throwable;
        }
        return result;
    }

    /**
     * 记录操作日志
     * @param opLogRecord 操作类型
     * @param user 方法执行前的操作用户
     * @param result 目标方法执行结果
     * @param args 方法参数
     * @param throwable 目标方法异常记录
     */
    private void addOpLog(OpLogRecord opLogRecord, User user, Object result,Object[] args,Throwable throwable) {
        logger.debug("==================记录操作日志===================");
        try {
            OpLog opLog = new OpLog();
            // 获取ip
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String ip = RequestUtils.getIpAddress(request);
            opLog.setOpIp(ip);
            // 增加操作结果描述
            OpLogType opLogType = opLogRecord.value();
            String desc = opLogType.getDesc();
            if (result != null && (result instanceof Response)) {// 虽然controller返回的都是Response，保险还是判断一下
                Response response = (Response) result;
                desc += response.getMsg();
            }
            // 操作类型
            opLog.setOpType(opLogType.getCode());
            Request req = catchRequest(args);
            Long id = null;
            if(user == null)// 如果执行前获取不到登录用户，则执行后再获取一次，用于记录登录/登出的特殊操作
                user = SubjectUtils.getCurrentUser();
            if(user != null) {
                opLog.setUserId(user.getId());
                // 只保留前2个参数
                if (req != null) {// json格式请求，获取Request对象组装描述信息
                    if (req.keySet().size() > 3) {// 去掉后面的元素
                        Set<String> ks = req.keySet();
                        // 先创建对象然后将keyset放入，避免迭代&删除失败
                        Set<String> keySet = new LinkedHashSet<>(ks);
                        Iterator<String> iterator = keySet.iterator();
                        int i=0;
                        while (iterator.hasNext()) {
                            String key = iterator.next();
                            if(i++ < 3){
                                continue;
                            }
                            req.remove(key);
                        }
                    }
                    desc += "【"+ JsonUtils.toJson(req)+"】";
                }else if((id = catchId(args)) != null){// 针对删除操作传递的主键参数
                    desc += "【id="+ id+"】";
                }
            }else{// 这种情况只有登录失败，记录用户名
                // 组装描述信息
                desc = (req == null?"":req.getString("username")) + ":" + desc + (throwable == null ? "" : "【"+ throwable.getMessage() + "】");
            }
            opLog.setDesc(desc);
            // -----------增加操作日志
            ThreadPoolFactory.submit(() -> {
                opLogSevice.addLog(opLog);
                logger.debug("==================日志记录完成===================");
            });
        } catch (Throwable t){
            logger.error("日志记录失败",t);
        }
    }

    /**
     * 从参数列表中获取Request
     * @param args
     * @return
     */
    private Request catchRequest(Object[] args) {
        if (args != null && args.length > 0) {
            for (Object arg : args) {
                if (arg instanceof Request) {
                    return (Request)arg;
                }
            }
        }
        return null;
    }

    private Long catchId(Object[] args) {
        if (args != null && args.length > 0) {
            for (Object arg : args) {
                if (arg instanceof Long) {
                    return (Long)arg;
                }
            }
        }
        return null;
    }


}
