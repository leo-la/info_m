package com.tfswx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class info {

    public static void main(String[] args) {
        SpringApplication.run(info.class, args);
    }

}
