package com.oym.gateway.swagger.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import springfox.documentation.swagger.web.*;

import java.util.List;
import java.util.Optional;

/**
 * swagger聚合接口
 * @author zsh
 * @date 2019/09/21
 */
@RestController
@RequestMapping("/swagger-resources")
public class SwaggerHandler {

    private static final Logger logger = LoggerFactory.getLogger(SwaggerHandler.class);

    @Autowired(required = false)
    private SecurityConfiguration securityConfiguration;

    @Autowired(required = false)
    private UiConfiguration uiConfiguration;


    private final SwaggerResourcesProvider swaggerResourcesProvider;

    @Autowired
    public SwaggerHandler(SwaggerResourcesProvider swaggerResourcesProvider) {
        this.swaggerResourcesProvider = swaggerResourcesProvider;
    }

    @GetMapping(value = "/configuration/security")
    public Mono<ResponseEntity<SecurityConfiguration>> securityConfiguration() {
        return Mono.just(new ResponseEntity<>(Optional.ofNullable(securityConfiguration)
                .orElse(SecurityConfigurationBuilder.builder().build()), HttpStatus.OK));
    }

    @GetMapping(value = "/configuration/ui")
    public Mono<ResponseEntity<UiConfiguration>> uiConfiguration() {
        return Mono.just(new ResponseEntity<>(Optional.ofNullable(uiConfiguration)
                .orElse(UiConfigurationBuilder.builder().build()), HttpStatus.OK));
    }

    @GetMapping("")
    public Mono<ResponseEntity<List<SwaggerResource>>> swaggerResources() {
        return Mono.just(new ResponseEntity<>(swaggerResourcesProvider.get(), HttpStatus.OK));
    }

}
