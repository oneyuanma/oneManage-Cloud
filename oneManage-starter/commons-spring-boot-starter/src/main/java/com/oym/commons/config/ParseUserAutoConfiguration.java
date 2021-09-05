package com.oym.commons.config;

import com.oym.commons.aop.ParseUserAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 通过header 解析用户数据
 *
 * @author oneyuanma
 * @date 2021/06/25
 */
@Configuration
public class ParseUserAutoConfiguration {

    @Bean
    public ParseUserAspect parseUserAspect() {
        return new ParseUserAspect();
    }

}
