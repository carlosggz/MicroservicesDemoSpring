package com.example.gatewayapi.services;

import com.example.gatewayapi.domain.ActorDetailsDto;
import com.example.gatewayapi.domain.Movie;
import com.example.gatewayapi.objectmothers.ActorsObjectMother;
import com.example.gatewayapi.objectmothers.MoviesObjectMother;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.val;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ActorDetailsServiceImplTest {

    private static final String HEADER = "Bearer something";

    MockWebServer mockWebServer;
    ActorDetailsServiceImpl actorDetailsService;

    ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    public void setup() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
        actorDetailsService = new ActorDetailsServiceImpl(
                WebClient.builder(),
                mockWebServer.url("/actor/{id}").toString(),
                mockWebServer.url("/search").toString()
        );
    }

    @AfterEach
    public void cleanup() throws IOException {
        mockWebServer.close();
        mockWebServer = null;
    }

    @Test
    void getActorWithNullIdReturnsEmpty() throws ExecutionException, InterruptedException {

        val result = actorDetailsService
                .getActor(null, HEADER)
                .block();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void getActorWithEmptyIdReturnsEmpty() throws ExecutionException, InterruptedException {

        val result = actorDetailsService
                .getActor("", HEADER)
                .block();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void getActorWithoutMoviesReturnActorInfoOnly() throws ExecutionException, InterruptedException, JsonProcessingException {

        val actor = ActorsObjectMother.getRandom(new ArrayList<>());
        val details = new ActorDetailsDto(actor, new ArrayList<>());
        mockWebServer.enqueue(getResponse(actor));

        val result = actorDetailsService
                .getActor(actor.getId(), HEADER)
                .block();

        assertNotNull(result);
        assertTrue(result.isPresent());
        assertEquals(details, result.get());
    }

    @Test
    void getActorWithMoviesReturnFullInfo() throws JsonProcessingException, ExecutionException, InterruptedException {

        val movies = List.of(MoviesObjectMother.getRandom(), MoviesObjectMother.getRandom());
        val actor = ActorsObjectMother.getRandom(movies.stream().map(Movie::getId).collect(Collectors.toList()));
        val details = new ActorDetailsDto(actor, movies);
        mockWebServer.enqueue(getResponse(actor));
        mockWebServer.enqueue(getResponse(movies));

        val result = actorDetailsService
                .getActor(actor.getId(), HEADER)
                .block();

        assertNotNull(result);
        assertTrue(result.isPresent());
        assertEquals(details, result.get());
    }

    private MockResponse getResponse(Object object) throws JsonProcessingException {
        return new MockResponse()
                .addHeader("Content-Type", "application/json; charset=utf-8")
                .setBody(mapper.writeValueAsString(object));
    }
}