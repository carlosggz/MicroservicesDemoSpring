package com.example.actorsapi.application;

import com.example.actorsapi.domain.Actor;
import com.example.actorsapi.domain.ActorsRepository;
import com.example.actorsapi.domain.LikeMovieDomainEvent;
import com.example.actorsapi.objectmothers.ActorsObjectMother;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LikeMovieHandlerTest {

    @Mock
    ActorsRepository repository;

    @InjectMocks
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