package com.example.shared.infrastructure;

import com.example.shared.domain.DomainEvent;
import lombok.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class RabbitEventBusTest {

    @NoArgsConstructor
    @Getter
    @Setter
    class FakeEvent extends DomainEvent {
        String myProperty;

        public FakeEvent(
                String eventId,
                String aggregateRootId,
                Date occurrenceDate,
                String myProperty) {
            super(eventId, aggregateRootId, occurrenceDate);
            this.myProperty = myProperty;
        }
    }

    @Mock
    RabbitTemplate rabbitTemplate;

    @InjectMocks
    RabbitEventBus eventBus;

    @Test
    void publishToQueueCallsAllItems() {

        val events = List.of(
                new FakeEvent("ev1", "123", new Date(), "1"),
                new FakeEvent("ev2", "123", new Date(), "1"),
                new FakeEvent("ev3", "123", new Date(), "1")
        );

        eventBus.publishToQueue(events);

        events.forEach(x -> then(rabbitTemplate).should(times(1)).convertAndSend(x.getEventId(), x));
    }
}