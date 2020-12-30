package com.example.actorsapi.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    /*
    @Bean
    Queue queue() {
        return new Queue(Constants.LIKES_QUEUE, false);
    }

	@Bean
	TopicExchange exchange() {
		return new TopicExchange("spring-boot-exchange");
	}

	@Bean
	Binding binding(Queue queue, TopicExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(Constants.LIKES_QUEUE);
	}

	@Bean
	SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
	MessageListenerAdapter listenerAdapter) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setQueueNames(Constants.LIKES_QUEUE);
		container.setMessageListener(listenerAdapter);
		return container;
	}

	@Bean
	MessageListenerAdapter listenerAdapter(ProductMessageListener receiver) {
		return new MessageListenerAdapter(receiver, "receiveMessage");
	}
    * */
}
