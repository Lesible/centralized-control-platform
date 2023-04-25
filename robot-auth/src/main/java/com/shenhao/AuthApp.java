package com.shenhao;

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author 何嘉豪
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan(basePackages = {"com.shenhao.mapper"})
public class AuthApp {

    public static void main(String[] args) {
        SpringApplication.run(AuthApp.class, args);
    }

}
