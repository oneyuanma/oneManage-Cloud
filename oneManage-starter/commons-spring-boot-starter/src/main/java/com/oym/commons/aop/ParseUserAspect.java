package com.oym.commons.aop;

import com.oym.commons.user.WebUser;
import com.oym.commons.utils.Argument;
import com.oym.commons.utils.StringUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;

/**
 * 解析header, 获取用户数据
 *
 * @author oneyuanma
 * @date 2021/06/25
 */
@Order(-1)
@Aspect
public class ParseUserAspect {

    private static final Logger logger = LoggerFactory.getLogger(ParseUserAspect.class);

    /**
     * Controller层切点
     */
    @Pointcut("execution(* com.oym..ctrl.*Controller.*(..))")
    public void controllerAspect() {
    }

    /**
     * 方法调用之前调用
     */
    @Before(value = "controllerAspect()")
    public void doBefore(JoinPoint joinPoint) {

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        String userInfo = request.getHeader("X-Auth-User");
        if (Argument.isNotBlank(userInfo)) {
            WebUser.resetWebUser();

            try {
                userInfo = URLDecoder.decode(userInfo, "UTF-8");
                logger.info("parseUserAspect userInfo: {}", userInfo);
            } catch (Exception e) {
                logger.error("parseUserAspect user decode err, msg:{}", e.getMessage());
            }

            String[] arr = userInfo.split(";");
            WebUser webUser = new WebUser();
            for (String s : arr) {
                String[] innerArr = s.split("=", 2);
                if (innerArr.length < 2) {
                    continue;
                }
                if ("userId".equals(innerArr[0])) {
                    webUser.setUserId(Long.parseLong(innerArr[1]));
                } else if ("language".equals(innerArr[0])) {
                    webUser.setLanguage((innerArr[1]));
                } else if ("timeZone".equals(innerArr[0])) {
                    webUser.setTimeZone(StringUtil.trim(innerArr[1]));
                } else if ("operator".equals(innerArr[0])) {
                    webUser.setOperator(StringUtil.trim(innerArr[1]));
                } else if ("username".equals(innerArr[0])) {
                    webUser.setUsername(StringUtil.trim(innerArr[1]));
                } else if ("avatar".equals(innerArr[0])) {
                    webUser.setAvatar(StringUtil.trim(innerArr[1]));
                } else if ("ip".equals(innerArr[0])) {
                    webUser.setIp(StringUtil.trim(innerArr[1]));
                } else if ("phone".equals(innerArr[0])) {
                    webUser.setPhone(StringUtil.trim(innerArr[1]));
                } else if ("email".equals(innerArr[0])) {
                    webUser.setEmail(StringUtil.trim(innerArr[1]));
                } else if ("sex".equals(innerArr[0])) {
                    webUser.setSex(StringUtil.trim(innerArr[1]));
                } else if ("loginType".equals(innerArr[0])) {
                    webUser.setLoginType(StringUtil.trim(innerArr[1]));
                } else if ("lastAccess".equals(innerArr[0])) {
                    webUser.setLastAccess(Long.parseLong(innerArr[1]));
                } else if ("ua".equals(innerArr[0])) {
                    webUser.setUa(StringUtil.trim(innerArr[1]));
                } else if ("token".equals(innerArr[0])) {
                    webUser.setToken(StringUtil.trim(innerArr[1]));
                } else if ("nickName".equals(innerArr[0])) {
                    webUser.setNickName(StringUtil.trim(innerArr[1]));
                }
            }
            WebUser.setCurrentUser(webUser);

        }
    }

}
