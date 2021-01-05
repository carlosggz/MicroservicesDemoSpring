package com.example.identityserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.example.identityserver", "com.example.shared"})
public class IdentityServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(IdentityServerApplication.class, args);
    }
}
