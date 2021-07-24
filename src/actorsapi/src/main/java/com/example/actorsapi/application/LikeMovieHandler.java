package com.example.actorsapi.application;

import com.example.actorsapi.domain.ActorsRepository;
import com.example.actorsapi.domain.LikeMovieDomainEvent;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import lombok.val;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class LikeMovieHandler {

    private final ActorsRepository repository;

    @Transactional
    @Synchronized
    public void handle(LikeMovieDomainEvent domainEvent) {

       val actors = repository.getByMovie(domainEvent.getAggregateRootId());

       if (actors.size() == 0){
           return;
       }

       actors.forEach(x-> x.setLikes(x.getLikes()+1));

       repository.save(actors);
    }
}
