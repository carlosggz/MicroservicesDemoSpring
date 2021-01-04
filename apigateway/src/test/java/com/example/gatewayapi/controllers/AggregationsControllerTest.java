package com.example.gatewayapi.controllers;

import com.example.gatewayapi.domain.ActorDetailsDto;
import com.example.gatewayapi.domain.Movie;
import com.example.gatewayapi.objectmothers.ActorsObjectMother;
import com.example.gatewayapi.objectmothers.MoviesObjectMother;
import com.example.gatewayapi.services.ActorDetailsService;
import lombok.val;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

class AggregationsControllerTest {

    ActorDetailsService actorDetailsService;
    AggregationsController controller;

    @BeforeEach
    public void setup(){
        actorDetailsService = mock(ActorDetailsService.class);
        controller = new AggregationsController(actorDetailsService);
    }

    @AfterEach
    public void cleanup(){
        actorDetailsService = null;
    }

    @Test
    void getInvalidActorReturnsNotFound() throws ExecutionException, InterruptedException {

        val id = "123";
        when(actorDetailsService.getActor(id)).thenReturn(Mono.just(Optional.empty()));

        val result = controller.getActorDetails(id).block();

        assertNotNull(result);
        assertEquals(HttpStatus.NOT_FOUND ,result.getStatusCode());
        assertFalse(result.hasBody());
    }

    @Test
    void getValidActorReturnsTheActor() throws ExecutionException, InterruptedException {

        val movies = List.of(
                MoviesObjectMother.getRandom(),
                MoviesObjectMother.getRandom(),
                MoviesObjectMother.getRandom()
        );
        val actor = ActorsObjectMother
                .getRandom(
                        movies
                        .stream()
                        .map(Movie::getId)
                        .collect(Collectors.toList())
        );

        val expected = new ActorDetailsDto(actor, movies);

        when(actorDetailsService.getActor(actor.getId())).thenReturn(Mono.just(Optional.of(expected)));

        val result = controller.getActorDetails(actor.getId()).block();

        assertNotNull(result);
        assertEquals(HttpStatus.OK ,result.getStatusCode());
        assertEquals(expected, result.getBody());
    }
}