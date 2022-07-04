package com.example.myseckill;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
//@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class MyseckillApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyseckillApplication.class, args);
    }

}
