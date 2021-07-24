package com.example.actorsapi.application;

import com.example.actorsapi.domain.Actor;
import com.example.actorsapi.domain.ActorsRepository;
import com.example.actorsapi.domain.LikeMovieDomainEvent;
import com.example.actorsapi.objectmothers.ActorsObjectMother;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LikeMovieHandlerTest {

    @Mock
    ActorsRepository repository;

    @InjectMocks
    LikeMovieHandler handler;

    @Captor
    ArgumentCaptor<List<Actor>> captorEvent;

    @Test
    void handleWithResultsIncreaseByOneEachActorLikes() {

        val movieId = "xxx";
        val likes = 10;
        List<Actor> items = List.of(
                ActorsObjectMother.getActorWithMovie(movieId, likes),
                ActorsObjectMother.getActorWithMovie(movieId + "x", likes+1),
                ActorsObjectMother.getActorWithMovie(movieId, likes+2)
        );
        val domainEvent = new LikeMovieDomainEvent();
        domainEvent.setAggregateRootId(movieId);
        when(repository.getByMovie(movieId)).thenReturn(items);

        handler.handle(domainEvent);

        then(repository).should(times(1)).getByMovie(movieId);
        then(repository).should(times(1)).save(any());

        verify(repository).save(captorEvent.capture());
        val likesResult = captorEvent.getValue();
        assertNotNull(likesResult);
        assertEquals(items.size(), likesResult.size());

        for(int index = 0; index < items.size(); index++) {
            assertEquals(likes+index+1, likesResult.get(index).getLikes());
        }
    }

    @Test
    void handleWithoutResultsDoNothing() {

        val movieId = "xxx";
        val likes = 10;
        List<Actor> items = List.of(
                ActorsObjectMother.getActorWithMovie(movieId, likes),
                ActorsObjectMother.getActorWithMovie(movieId + "x", likes+1),
                ActorsObjectMother.getActorWithMovie(movieId, likes+2)
        );
        val domainEvent = new LikeMovieDomainEvent();
        domainEvent.setAggregateRootId(movieId);
        when(repository.getByMovie(movieId)).thenReturn(new ArrayList<>());

        handler.handle(domainEvent);

        then(repository).should(times(1)).getByMovie(movieId);
        then(repository).should(times(0)).save(any());
    }
}