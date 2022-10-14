package com.yujiangjun;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class IMBackendApp {
    public static void main(String[] args) {
        System.setProperty("hadoop.home.dir","/opt");
        SpringApplication.run(IMBackendApp.class,args);
    }
}