package com.drawnet.artcollab.monetizationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class MonetizationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MonetizationServiceApplication.class, args);
    }

}
