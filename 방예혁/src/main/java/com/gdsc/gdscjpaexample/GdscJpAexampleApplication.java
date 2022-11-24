package com.gdsc.gdscjpaexample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class GdscJpAexampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(GdscJpAexampleApplication.class, args);
    }

}
