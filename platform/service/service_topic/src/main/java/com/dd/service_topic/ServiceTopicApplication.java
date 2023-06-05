package com.dd.service_topic;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.dd")
@MapperScan("com.dd.service_topic.mapper")
@MapperScan("com.dd.security.mapper")
public class ServiceTopicApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceTopicApplication.class,args);
    }
}
