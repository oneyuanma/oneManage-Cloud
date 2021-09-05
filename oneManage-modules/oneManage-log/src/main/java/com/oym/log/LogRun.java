package com.oym.log;


import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import com.oym.commons.config.SwaggerConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
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
@SpringBootApplication
@ImportAutoConfiguration({ SwaggerConfiguration.class})
@MapperScan(annotationClass = Repository.class, basePackages = "com.oym.log.dao")
public class LogRun {

    public static void main(String[] args) {
        SpringApplication.run(LogRun.class, args);
        System.out.println("oneManage log 启动成功");
    }

}
