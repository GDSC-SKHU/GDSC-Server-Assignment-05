package com.gdsc.jpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class MyoungjinseoApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyoungjinseoApplication.class, args);
    }

}
