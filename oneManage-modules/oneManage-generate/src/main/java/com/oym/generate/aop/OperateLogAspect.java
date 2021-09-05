package com.oym.generate.aop;

import com.oym.commons.anonation.OperateLog;
import com.oym.commons.user.WebUser;
import com.oym.commons.utils.Argument;
import com.oym.commons.utils.GsonUtil;
import com.oym.generate.domain.message.OperateMessage;
import com.oym.generate.transform.LogTransform;
import com.oym.kafka.client.KafkaClient;
import com.oym.log.api.LogApi;
import com.oym.system.api.KvApi;
import com.oym.system.api.domain.request.GetStringValueRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * 操作日志保存切面
 *
 * @author oneyuanma
 * @date 2021/07/22
 */
@Aspect
@Slf4j
public class OperateLogAspect {

    @Autowired
    private KafkaClient kafkaClient;

    @Autowired
    private LogApi logApi;

    @Autowired
    private KvApi kvApi;

    /**
     * 日志保存方式
     */
    private static final String KAFKA = "kafka";

    @Pointcut("execution(public * com.oym.*.ctrl.*(..)) && (@annotation(com.oym.commons.anonation.OperateLog))")
    public void aspect() {
    }

    @Around(value = "aspect() && @annotation(operateLog)", argNames = "joinPoint,operateLog")
    public Object methodAround(ProceedingJoinPoint joinPoint, OperateLog operateLog) throws Throwable {
        // 获取开始时间
        long startTime = System.currentTimeMillis();
        // 获取返回结果集
        Object obj = joinPoint.proceed(joinPoint.getArgs());
        // 获取方法执行时间
        long executeTime = System.currentTimeMillis() - startTime;
        try {

            OperateMessage message = new OperateMessage();
            // 当前登录人
            WebUser webUser = WebUser.getCurrentUser();
            if (Argument.isNotNull(webUser)) {
                message.setUid(webUser.getUserId());
                message.setUserName(webUser.getUsername());
            }
            message.setIp(webUser.getIp());
            message.setOperatePath(operateLog.operatePath());
            message.setModule(operateLog.module());
            message.setParameter(GsonUtil.toJson(joinPoint.getArgs()));
            message.setResult(GsonUtil.toJson(joinPoint.proceed()));
            message.setType(operateLog.type().getValue());
            message.setExecuteTime(executeTime);
            message.setTime(new Date());
            /**
             * 操作日志保存，这边提供了两种方式
             * 1.通过kafka消息把登录日志存入ES
             * 2.通过feign调用讲登录日志存入数据库
             * 具体采用哪种有系统KV控制
             */
            String value = kvApi.getStringValue(new GetStringValueRequest("operate_log_type", "kafka"));
            if (KAFKA.equals(value)) {
                // 通过kafka消息将日志存入ES
                kafkaClient.send("operate_log_topic", message);
            } else {
                // 通过feign调用将日志保存到数据库
                logApi.operateLogSave(LogTransform.INS.transfer(message));
            }

        } catch (Exception e) {
            log.error("OperateLogAspect operate log save err,msg:{}", ExceptionUtils.getStackTrace(e));
        }
        return obj;
    }
}
