package com.edukus.diabeto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class DiabetoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DiabetoApplication.class, args);
    }

}
