package com.oym.generate;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import com.oym.commons.config.SwaggerConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.stereotype.Repository;

/**
 * 代码生成启动入口
 *
 * @author oneyuanma
 * @date 2021-06-09
 */
@EnableApolloConfig
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.oym.*")
@SpringBootApplication(scanBasePackages = {"com.oym.*"})
@ImportAutoConfiguration({SwaggerConfiguration.class})
@MapperScan(annotationClass = Repository.class, basePackages = "com.oym.generate.dao")
public class GenerateRun {


    public static void main(String[] args) {
        SpringApplication.run(GenerateRun.class, args);
        System.out.println("oneManage generate 启动成功");
    }

}

