package com.example.gatewayapi.services;

import com.example.gatewayapi.domain.Actor;
import com.example.gatewayapi.domain.ActorDetailsDto;
import com.example.gatewayapi.domain.Movie;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Service
public class ActorDetailsServiceImpl implements ActorDetailsService {

    private final WebClient.Builder webClientBuilder;
    private final String actorDetailsUri;
    private final String moviesSearchUri;

    public ActorDetailsServiceImpl(
            WebClient.Builder webClientBuilder,
            @Value("${app.actor_details}") String actorDetailsUri,
            @Value("${app.movie_search}") String moviesSearchUri) {
        this.webClientBuilder = webClientBuilder;
        this.actorDetailsUri = actorDetailsUri;
        this.moviesSearchUri = moviesSearchUri;
    }

    @Override
    public Optional<ActorDetailsDto> getActor(String id, String authorization) throws ExecutionException, InterruptedException {

        return id == null || id.trim().length() == 0
                ? Optional.empty()
                : getActorResponse(id, authorization)
                    .flatMap(actor -> (actor.getMovies() == null || actor.getMovies().size() == 0)
                        ? Mono.just(Optional.of(new ActorDetailsDto(actor, List.of())))
                        : getMovies(List.copyOf(actor.getMovies()), authorization)
                            .collectList()
                            .map(movies -> Optional.of(new ActorDetailsDto(actor, movies)))
                )
                .block();
    }

    Mono<Actor> getActorResponse(String id, String authorization) {

        return webClientBuilder
                .build()
                .get()
                .uri(actorDetailsUri, id)
                .header("Authorization", authorization)
                .retrieve()
                .bodyToMono(Actor.class);
    }

    Flux<Movie> getMovies(List<String> ids, String authorization) {
        return webClientBuilder
                .build()
                .post()
                .uri(moviesSearchUri)
                .header("Authorization", authorization)
                .body(Mono.just(ids), List.class)
                .retrieve()
                .bodyToFlux(Movie.class);
    }
}
