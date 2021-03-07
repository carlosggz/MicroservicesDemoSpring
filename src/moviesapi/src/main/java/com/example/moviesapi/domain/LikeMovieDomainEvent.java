package com.example.moviesapi.domain;

import com.example.shared.domain.DomainEvent;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class LikeMovieDomainEvent extends DomainEvent {

    int likes;

    public LikeMovieDomainEvent(@NonNull String eventId, @NonNull String aggregateRootId, int likes){
        super(eventId, aggregateRootId, new Date());
        this.likes = likes;
    }
}