package com.example.moviesapi.domain;

import com.example.shared.domain.DomainEvent;
import lombok.Getter;
import lombok.NonNull;

import java.time.LocalDateTime;

@Getter
public class LikeMovieDomainEvent extends DomainEvent {

    int likes;

    public LikeMovieDomainEvent(@NonNull String eventId, @NonNull String aggregateRootId, int likes){
        super(eventId, aggregateRootId, LocalDateTime.now());
        this.likes = likes;
    }
}