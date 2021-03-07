package com.example.actorsapi.application;

import com.example.actorsapi.domain.Actor;
import com.example.actorsapi.domain.ActorsRepository;
import com.example.actorsapi.domain.LikeMovieDomainEvent;
import com.example.actorsapi.objectmothers.ActorsObjectMother;
import com.netflix.discovery.converters.Auto;
import lombok.val;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@SpringBootTest
class LikeMovieHandlerTest {

    @MockBean
    ActorsRepository repository;

    @Autowired
    LikeMovieHandler handler;

    @Test
    void handleWithResultsIncreaseByOneEachActorLikes() {

        val movieId = "xxx";
        val likes = 10;
        List<Actor> items = List.of(
                getActorWithMovie(movieId, likes),
                getActorWithMovie(movieId + "x", likes+1),
                getActorWithMovie(movieId, likes+2)
        );
        val domainEvent = new LikeMovieDomainEvent();
        domainEvent.setAggregateRootId(movieId);
        when(repository.getByMovie(movieId)).thenReturn(items);
        ArgumentCaptor<List<Actor>> captorEvent = ArgumentCaptor.forClass(List.class);

        handler.handle(domainEvent);

        then(repository).should(times(1)).getByMovie(movieId);
        then(repository).should(times(1)).save(any());

        verify(repository).save(captorEvent.capture());
        assertEquals(items.size(), captorEvent.getValue().size());

        for(int index = 0; index < items.size(); index++) {
            assertEquals(likes+index+1, captorEvent.getValue().get(index).getLikes());
        }
    }

    @Test
    void handleWithoutResultsDoNothing() {

        val movieId = "xxx";
        val likes = 10;
        List<Actor> items = List.of(
                getActorWithMovie(movieId, likes),
                getActorWithMovie(movieId + "x", likes+1),
                getActorWithMovie(movieId, likes+2)
        );
        val domainEvent = new LikeMovieDomainEvent();
        domainEvent.setAggregateRootId(movieId);
        when(repository.getByMovie(movieId)).thenReturn(new ArrayList<>());

        handler.handle(domainEvent);

        then(repository).should(times(1)).getByMovie(movieId);
        then(repository).should(times(0)).save(any());
    }

    private Actor getActorWithMovie(String movieId, int likes){
        val actor = ActorsObjectMother.getRandomDomainEntity();
        val movies = new HashSet<String>(actor.getMovies());
        movies.add(movieId);
        actor.setMovies(movies);
        actor.setLikes(likes);
        return actor;
    }
}