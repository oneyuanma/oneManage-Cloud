package com.oym.gateway.filter;

import com.oym.commons.cons.ReturnCode;
import com.oym.commons.user.UserLoginInfo;
import com.oym.commons.user.WebUser;
import com.oym.commons.utils.Argument;
import com.oym.gateway.commons.ReactiveParseTokenManager;
import com.oym.gateway.commons.util.AntPathMather;
import com.oym.redis.client.RedisClient;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.RequestPath;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URLEncoder;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * 解析token 获取已登录用户信息
 *
 * @author oneyuanma
 * @date 2021/06/25
 */
@Component
@ConfigurationProperties(prefix = "url.form")
public class ParseTokenFilter implements GlobalFilter, Ordered {

    private static final Logger logger = LoggerFactory.getLogger(ParseTokenFilter.class);

    private static final String USER_TOKEN = "X-Token";

    private static final String SLASH = "/";

    protected static final long TOKEN_VALID_MILLIS_SECOND = 1800;

    @Autowired
    private RedisClient redisClient;

    /**
     * 免登陆url
     */
    private Set<String> neededLoginUrl = new HashSet<>();

    public void setNeededLoginUrl(Set<String> neededLoginUrl) {
        this.neededLoginUrl = neededLoginUrl;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        logger.info("parse cookie start ...");

        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        try {
            RequestPath requestPath = request.getPath();
            String path = requestPath.value();
            if (path.startsWith(SLASH)) {
                path = path.substring(1);
                path = path.substring(path.indexOf(SLASH));
            }

            // 无需校验登录url
            AntPathMather antPathMather = new AntPathMather();
            String finalPath = path;
            Optional<String> optional = neededLoginUrl.stream().filter(a -> antPathMather.matches(a, finalPath)).findFirst();
            if (optional.isPresent()) {
                return chain.filter(exchange);
            }

            String authToken = request.getHeaders().getFirst(USER_TOKEN);
            // 没有token， 请重新登录
            if (Argument.isBlank(authToken)) {
                return errResponse(response, ReturnCode.NO_TOKEN_ERROR);
            }

            UserLoginInfo userLoginInfo = (UserLoginInfo) redisClient.get(authToken);
            // 登录已过期,请重新登录
            if (Argument.isNull(userLoginInfo)) {
                return errResponse(response, ReturnCode.USER_LOGIN_EXPIRED);
            }

            // 刷新token有效期
            redisClient.set(authToken, userLoginInfo, TOKEN_VALID_MILLIS_SECOND);

            // token -> webUser
            new ReactiveParseTokenManager(request, response, userLoginInfo);

            WebUser webUser = WebUser.getCurrentUser();
            String authUser = URLEncoder.encode(webUser.getInfo().get(), "UTF-8");

            ServerHttpRequest req = exchange.getRequest().mutate()
                    .header("X-Auth-User", authUser).build();

            ServerWebExchange swe = exchange.mutate().request(req).build();

            logger.info("parse token end, get data:{}", webUser.getInfo());
            return chain.filter(swe);

        } catch (Exception e) {
            logger.error("parse token err, msg:{}", ExceptionUtils.getStackTrace(e));
        }

        return chain.filter(exchange);
    }

    private Mono<Void> errResponse(ServerHttpResponse response, ReturnCode returnCode) {
        return AuthFilter.getVoidMono(response, returnCode);
    }

    @Override
    public int getOrder() {
        return -200;
    }

}
