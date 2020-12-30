package com.example.moviesapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.example.moviesapi", "com.example.shared"})
public class MoviesapiApplication {

    public static void main(String[] args) {
        SpringApplication.run(MoviesapiApplication.class, args);
    }

}
