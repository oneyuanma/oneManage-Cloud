package com.oym.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 网关应用启动入口
 *
 * @author oneyuanma
 * @date 2021-06-09
 */
//@EnableApolloConfig
//@ImportAutoConfiguration({ GatewaySwaggerResourceProvider.class})
@EnableFeignClients(basePackages="com.oym.*")
@SpringBootApplication
public class GatewayRun {

    public static void main(String[] args) {
        SpringApplication.run(GatewayRun.class, args);
        System.out.println("oneManage gateway 启动成功");
    }


}

