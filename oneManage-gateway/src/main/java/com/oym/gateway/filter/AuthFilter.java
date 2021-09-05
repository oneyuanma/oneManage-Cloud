package com.oym.gateway.filter;

import cn.hutool.json.JSONUtil;
import com.oym.commons.base.response.OymResponse;
import com.oym.commons.cons.ReturnCode;
import com.oym.commons.user.WebUser;
import com.oym.commons.utils.Argument;
import com.oym.gateway.commons.util.AntPathMather;
import com.oym.gateway.commons.util.PrivilegeCheckUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.RequestPath;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * 权限验证
 *
 * @author oneyuanma
 * @date 2021/06/25
 */
@Component
@ConfigurationProperties(prefix = "url.form")
public class AuthFilter implements GlobalFilter, Ordered {

    private static final Logger logger = LoggerFactory.getLogger(AuthFilter.class);

    /**
     * 免验证权限url
     */
    private Set<String> notPermission = new HashSet<>();

    public void setNotPermission(Set<String> notPermission) {
        this.notPermission = notPermission;
    }

    private static final String SLASH = "/";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        RequestPath requestPath = request.getPath();
        String path = requestPath.value();
        if (path.startsWith(SLASH)) {
            path = path.substring(1);
            path = path.substring(path.indexOf(SLASH));
        }

        // 权限校验白名单
        AntPathMather antPathMather = new AntPathMather();
        String finalPath = path;
        Optional<String> optional = notPermission.stream().filter(a -> antPathMather.matches(a, finalPath)).findFirst();
        if (optional.isPresent()) {
            return chain.filter(exchange);
        }

        WebUser webUser = WebUser.getCurrentUser();
        if (Argument.isNull(webUser) || Argument.isNull(webUser.getUserId())) {
            logger.info("AuthFilter user login expired");
            return errResponse(response, ReturnCode.USER_LOGIN_EXPIRED);
        }

        // 校验权限
        if (PrivilegeCheckUtils.check(webUser.getUserId(), path)) {
            return chain.filter(exchange);
        }

        // 没有权限
        logger.info("AuthFilter usernopermission locked err, userId:{}, path:{}", webUser.getUserId(), path);
        return errResponse(response, ReturnCode.USER_NOPERMISSION_LOCKED_ERROR);
    }

    private Mono<Void> errResponse(ServerHttpResponse response, ReturnCode returnCode) {
        return getVoidMono(response, returnCode);
    }

    static Mono<Void> getVoidMono(ServerHttpResponse response, ReturnCode returnCode) {
        OymResponse resp = OymResponse.error(returnCode);
        byte[] bytes = JSONUtil.toJsonStr(resp).getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = response.bufferFactory().wrap(bytes);
        response.setStatusCode(HttpStatus.OK);
        response.getHeaders().add(org.apache.http.HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE);
        return response.writeWith(Flux.just(buffer));
    }

    @Override
    public int getOrder() {
        return -190;
    }

}
