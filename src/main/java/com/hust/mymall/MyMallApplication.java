package com.hust.mymall;

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.hust.mymall.dao")
public class MyMallApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyMallApplication.class, args);
    }
}
