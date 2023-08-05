package com.homestay;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.homestay.mapper")
public class HomestayApp {

    public static void main(String[] args) {
        SpringApplication.run(HomestayApp.class,args);
    }
}
