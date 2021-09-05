//package com.oym.monitor.config;
//
//import com.alibaba.druid.support.http.StatViewServlet;
//import org.springframework.boot.web.servlet.ServletRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///**
// * 线程池监控信息查看配置
// */
//@Configuration
//public class DruidConfig {
//
//    @Bean
//    public ServletRegistrationBean<StatViewServlet> statViewServlet() {
//        ServletRegistrationBean<StatViewServlet> srb = new ServletRegistrationBean
//                (new StatViewServlet(), "/druid/*");
//        //IP白名单(没有配置或者为空，则允许所有访问)
//        srb.addInitParameter("allow", "127.0.0.1");
//        //IP黑名单(黑白均有时，deny优先于allow) :
//        //如果满足deny的即提示：Sorry, you are not permitted to view this page.
//        srb.addInitParameter("deny", "192.168.1.100");
//        //账号参数名必须为loginUsername
//        srb.addInitParameter("loginUsername", "root");
//        //密码参数名必须为loginPassword
//        srb.addInitParameter("loginPassword", "root");
//        //是否能够重置数据
//        srb.addInitParameter("resetEnable", "false");
//        return srb;
//    }
//}
