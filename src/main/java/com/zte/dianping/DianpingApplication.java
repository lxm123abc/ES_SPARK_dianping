package com.zte.dianping;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = {"com.zte.dianping"})
@MapperScan("com.zte.dianping.dao")
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableScheduling
public class DianpingApplication {

    public static void main(String[] args) {
        SpringApplication.run(DianpingApplication.class, args);
        System.out.println("系统启动成功，用户访问地址为：http://localhost:8010/static/index.html");
        System.out.println("系统启动成功，商家访问地址为：http://localhost:8010/admin/admin/index");
    }

}
