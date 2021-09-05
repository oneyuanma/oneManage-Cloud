package com.oym.monitor;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 监控管理应用启动入口
 *
 * @author oneyuanma
 * @date 2021-06-09
 */
@EnableAdminServer
@SpringBootApplication(scanBasePackages = {"com.oym.*"})
public class MonitorRun {

    public static void main(String[] args) {
        SpringApplication.run(MonitorRun.class, args);
        System.out.println("oneManage monitor 启动成功");
    }


}

