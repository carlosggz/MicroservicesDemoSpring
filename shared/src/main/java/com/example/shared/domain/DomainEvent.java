package com.example.shared.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public abstract class DomainEvent implements Serializable {

    String eventId;
    String aggregateRootId;
    LocalDateTime occurrenceDate;
}
