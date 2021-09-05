package com.oym.system;


import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import com.oym.commons.config.SwaggerConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Repository;
import org.zxp.esclientrhl.annotation.EnableESTools;

/**
 * 系统应用启动入口
 *
 * @author oneyuanma
 * @date 2021-06-09
 */
@EnableESTools
@EnableApolloConfig
@EnableDiscoveryClient
@EnableFeignClients(basePackages="com.oym.*")
@SpringBootApplication(scanBasePackages = {"com.oym.*"})
@ImportAutoConfiguration({ SwaggerConfiguration.class})
@MapperScan(annotationClass = Repository.class, basePackages = "com.oym.system.dao")
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class SystemRun {

    public static void main(String[] args) {
        SpringApplication.run(SystemRun.class, args);
        System.out.println("oneManage system 启动成功");
    }

}

