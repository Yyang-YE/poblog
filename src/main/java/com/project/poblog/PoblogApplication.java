package com.project.poblog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class PoblogApplication {

    public static void main(String[] args) {
        SpringApplication.run(PoblogApplication.class, args);
    }

}
