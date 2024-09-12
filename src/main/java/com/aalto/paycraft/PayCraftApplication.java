package com.aalto.paycraft;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
public class PayCraftApplication {
    public static void main(String[] args) {
        SpringApplication.run(PayCraftApplication.class, args);
    }
}
