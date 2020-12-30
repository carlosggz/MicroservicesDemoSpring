package com.example.actorsapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.example.actorsapi", "com.example.shared"})
public class ActorsapiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ActorsapiApplication.class);
    }
}
