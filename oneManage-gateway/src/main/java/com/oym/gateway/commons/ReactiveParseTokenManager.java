package com.oym.gateway.commons;

import com.oym.commons.user.UserLoginInfo;
import com.oym.commons.user.WebUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * token -> WebUser
 *
 * @author oneyuanma
 * @date 2021/06/25
 */
public class ReactiveParseTokenManager {

    private static final Logger logger = LoggerFactory.getLogger(ReactiveParseTokenManager.class);

    private static final String USER_TOKEN = "User-Agent";
    private static final String UNKNOWN = "unknown";
    private static final String COMMA = ",";

    private static final String USER_AGENT = "User-Agent";

    private ServerHttpRequest request;
    private ServerHttpResponse response;

    public ReactiveParseTokenManager(ServerHttpRequest request, ServerHttpResponse response, UserLoginInfo user) {
        this.response = response;
        this.request = request;
        init(user);
    }

    protected void init(UserLoginInfo user) {

        String ua = request.getHeaders().getFirst(USER_AGENT);

        WebUser webUser = new WebUser();

        webUser.setUserId(user.getId());
        webUser.setUsername(user.getUsername());
        webUser.setNickName(user.getNickName());
        webUser.setEmail(user.getEmail());
        webUser.setPhone(user.getPhone());
        webUser.setSex(user.getSex());
        webUser.setUa(ua);
        webUser.setIp(getClientIp(request));
        webUser.setToken(user.getToken());
        webUser.setLastAccess(LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli());
//        webUser.setLanguage();
//        webUser.setOperator();
//        webUser.setTimeZone();
//        webUser.setUuid();
        WebUser.setCurrentUser(webUser);
    }

    private String getClientIp(ServerHttpRequest request) {
        HttpHeaders headers = request.getHeaders();
        String ip = headers.getFirst("x-forwarded-for");
        if (ip != null && ip.length() != 0 && !UNKNOWN.equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
            if (ip.indexOf(COMMA) != -1) {
                ip = ip.split(COMMA)[0];
                return ip;
            }
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = headers.getFirst("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = headers.getFirst("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = headers.getFirst("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = headers.getFirst("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = headers.getFirst("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddress().getAddress().getHostAddress();
        }
        return ip;
    }
}
