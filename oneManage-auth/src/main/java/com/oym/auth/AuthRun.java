package com.oym.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 鉴权应用启动入口
 *
 * @author oneyuanma
 * @date 2021-06-09
 */
@EnableDiscoveryClient
@EnableFeignClients(basePackages="com.oym.*")
@SpringBootApplication(scanBasePackages = {"com.oym.*"})
public class AuthRun {

    public static void main(String[] args) {
        SpringApplication.run(AuthRun.class, args);
        System.out.println("oneManage auth 启动成功");
    }


}

