package com.oym.gateway.filter;

import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONUtil;
import com.oym.commons.user.WebUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class TraceIdFilter implements GlobalFilter, Ordered {

    private static final Logger logger = LoggerFactory.getLogger(TraceIdFilter.class);

    private final static String TRACE_ID = "traceId";

    private static final String REQUEST_TIME_BEGIN = "requestTimeBegin";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        exchange.getAttributes().put(REQUEST_TIME_BEGIN, System.currentTimeMillis());

        ServerHttpRequest request = exchange.getRequest();

        String uuid = IdUtil.objectId();
        MDC.put(TRACE_ID, uuid);

        logger.info("request start, path:{}, headers:{}, queryParams:{}", request.getPath(),
                JSONUtil.toJsonStr(request.getHeaders()), JSONUtil.toJsonStr(request.getQueryParams()));

        ServerHttpRequest host = exchange.getRequest().mutate()
                .header(TRACE_ID, uuid)
                .build();

        //将现在的request 变成 change对象
        ServerWebExchange build = exchange.mutate().request(host).build();

        return chain.filter(build).then(
                Mono.fromRunnable(() -> {
                    String uid = exchange.getAttribute(TRACE_ID);
                    MDC.put(TRACE_ID, uid);
                    Long startTime = exchange.getAttribute(REQUEST_TIME_BEGIN);
                    if (startTime != null) {
                        long du = System.currentTimeMillis() - startTime;
                        MDC.put("du", String.valueOf(du));
                        logger.info("request end, data:{}, duration:{}ms", exchange.getRequest().getURI().getRawPath(), du);
                    }
                    // webuser set null
                    WebUser.resetWebUser();
                    MDC.clear();
                })
        );
    }

    @Override
    public int getOrder() {
        return -210;
    }

}
