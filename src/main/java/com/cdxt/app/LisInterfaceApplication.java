package com.cdxt.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })//exclude防止动态数据源循环依赖
public class LisInterfaceApplication {

    public static void main(String[] args) {
        SpringApplication.run(LisInterfaceApplication.class, args);
    }

}
