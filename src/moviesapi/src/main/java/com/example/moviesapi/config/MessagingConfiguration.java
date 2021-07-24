package com.example.moviesapi.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessagingConfiguration {
    @Bean
    Queue queue() { return new Queue(Constants.LIKES_QUEUE, false); }
}
