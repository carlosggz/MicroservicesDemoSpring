package com.example.actorsapi.config;

import com.example.actorsapi.application.LikeMovieHandler;
import com.example.actorsapi.domain.LikeMovieDomainEvent;
import lombok.AllArgsConstructor;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.annotation.Payload;

@Configuration
@AllArgsConstructor
public class RabbitMQConfig {

    private final LikeMovieHandler likeMovieHandler;

    @Bean
    Queue queue() { return new Queue(Constants.LIKES_QUEUE, false); }

    @Bean
    public MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }

    @RabbitListener(queues = Constants.LIKES_QUEUE)
    public void processLikeMovie(@Payload LikeMovieDomainEvent domainEvent) {
        likeMovieHandler.handle(domainEvent);
    }
}
