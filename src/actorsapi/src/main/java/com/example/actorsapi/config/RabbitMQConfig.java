package com.example.actorsapi.config;

import com.example.actorsapi.application.LikeMovieHandler;
import com.example.actorsapi.domain.LikeMovieDomainEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.annotation.Payload;

@Configuration
@RequiredArgsConstructor
public class RabbitMQConfig {

    final LikeMovieHandler likeMovieHandler;

    @Bean
    Queue queue() { return new Queue(Constants.LIKES_QUEUE, false); }

    @RabbitListener(queues = Constants.LIKES_QUEUE)
    public void processLikeMovie(@Payload LikeMovieDomainEvent domainEvent) {
        likeMovieHandler.handle(domainEvent);
    }
}
