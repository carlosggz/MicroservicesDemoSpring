package com.example.shared.infrastructure;

import com.example.shared.domain.DomainEvent;
import com.example.shared.domain.EventBus;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class RabbitEventBus implements EventBus {

    private final RabbitTemplate rabbitTemplate;

    @Override
    public void publishToQueue(@NonNull List<? extends DomainEvent> events) {
        events.forEach(e -> rabbitTemplate.convertAndSend(e.getEventId(), e));
    }
}
