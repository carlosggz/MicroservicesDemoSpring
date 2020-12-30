package com.example.moviesapi.domain;

import com.example.moviesapi.config.Constants;
import com.example.shared.domain.DomainEvent;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class LikeMovieDomainEvent extends DomainEvent {

    int likes;

    public LikeMovieDomainEvent(Movie movie){
        super(Constants.LIKES_QUEUE, movie.getId(), LocalDateTime.now());
        likes = movie.getLikes();
    }
}