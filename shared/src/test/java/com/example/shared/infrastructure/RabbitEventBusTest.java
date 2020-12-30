package com.example.shared.infrastructure;

import com.example.shared.domain.DomainEvent;
import lombok.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

class RabbitEventBusTest {

    @NoArgsConstructor
    @Getter
    @Setter
    class FakeEvent extends DomainEvent {
        String myProperty;

        public FakeEvent(
                String eventId,
                String aggregateRootId,
                LocalDateTime occurrenceDate,
                String myProperty) {
            super(eventId, aggregateRootId, occurrenceDate);
            this.myProperty = myProperty;
        }
    }

    RabbitTemplate rabbitTemplate;
    RabbitEventBus eventBus;

    @BeforeEach
    public void setup(){
        rabbitTemplate = mock(RabbitTemplate.class);
        eventBus = new RabbitEventBus(rabbitTemplate);
    }

    @AfterEach
    public void clear(){
        eventBus = null;
        rabbitTemplate = null;
    }

    @Test
    void publishToQueueCallsAllItems() {

        val events = List.of(
                new FakeEvent("ev1", "123", LocalDateTime.now(), "1"),
                new FakeEvent("ev2", "123", LocalDateTime.now(), "1"),
                new FakeEvent("ev3", "123", LocalDateTime.now(), "1")
        );

        eventBus.publishToQueue(events);

        events.forEach(x -> then(rabbitTemplate).should(times(1)).convertAndSend(x.getEventId(), x));
    }
}