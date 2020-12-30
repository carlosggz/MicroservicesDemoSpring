package com.example.actorsapi.application;

import com.example.actorsapi.domain.ActorsRepository;
import com.example.actorsapi.domain.LikeMovieDomainEvent;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@AllArgsConstructor
public class LikeMovieHandler {

    private final ActorsRepository repository;

    @Transactional
    public void handle(LikeMovieDomainEvent domainEvent) {

       val actors = repository.getByMovie(domainEvent.getAggregateRootId());

       if (actors.size() == 0){
           return;
       }

       actors.forEach(x-> x.setLikes(x.getLikes()+1));

       repository.save(actors);
    }
}
