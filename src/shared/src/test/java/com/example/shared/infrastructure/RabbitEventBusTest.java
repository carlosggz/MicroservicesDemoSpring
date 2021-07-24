package com.example.shared.infrastructure;

import com.example.shared.domain.DomainEvent;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.BDDMockito.then;
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
                LocalDateTime occurrenceDate,
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
                new FakeEvent("ev1", "123", LocalDateTime.now(), "1"),
                new FakeEvent("ev2", "123", LocalDateTime.now(), "1"),
                new FakeEvent("ev3", "123", LocalDateTime.now(), "1")
        );

        eventBus.publishToQueue(events);

        events.forEach(x -> then(rabbitTemplate).should(times(1)).convertAndSend(x.getEventId(), x));
    }
}