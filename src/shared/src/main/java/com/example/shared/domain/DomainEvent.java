package com.example.shared.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    LocalDateTime occurrenceDate;
}
