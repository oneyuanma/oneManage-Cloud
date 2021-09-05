package com.oym.commons.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * swagger 配置类
 *
 * @author oneyuanma
 * @date 2021/09/04
 */
@EnableSwagger2
@Configuration
public class SwaggerConfiguration {

    /**
     * controller接口所在的包
     */
    @Value("${swagger.basePackage:com.oym}")
    private String basePackage;
    /**
     * 当前文档的标题
     */
    @Value("${swagger.title:oneManage RESTful APIs}")
    private String title;
    /**
     * 当前文档的详细描述
     */
    @Value("${swagger.description:系统功能api文档}")
    private String description;
    /**
     * 当前文档的版本
     */
    @Value("${swagger.version:1.0.1}")
    private String version;

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage(basePackage))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(title)
                .description(description)
                .version(version)
                .build();
    }
}
