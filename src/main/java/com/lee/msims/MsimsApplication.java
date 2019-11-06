package com.lee.msims;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.lee.msims.mapper")
@ComponentScan(basePackages = {"com.lee.msims.service", "com.lee.msims.shiro", "com.lee.msims.util", "com.lee.msims.controller"})
public class MsimsApplication {
    public static void main(String[] args) {
        SpringApplication.run(MsimsApplication.class, args);
    }

}
