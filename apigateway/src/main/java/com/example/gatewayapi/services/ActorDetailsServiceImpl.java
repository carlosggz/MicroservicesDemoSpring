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
            @Value("${custom.actor_details}") String actorDetailsUri,
            @Value("${custom.movie_search}") String moviesSearchUri) {
        this.webClientBuilder = webClientBuilder;
        this.actorDetailsUri = actorDetailsUri;
        this.moviesSearchUri = moviesSearchUri;
    }

    @Override
    public Mono<Optional<ActorDetailsDto>> getActor(String id) throws ExecutionException, InterruptedException {

        if (id == null || id.trim().length() == 0)
            return Mono.just(Optional.empty());

        return getActorResponse(id)
                .flatMap(actor -> (actor.getMovies() == null || actor.getMovies().size() == 0)
                        ? Mono.just(Optional.of(new ActorDetailsDto(actor, new ArrayList<>())))
                        : getMovies(new ArrayList<>(actor.getMovies()))
                            .collectList()
                            .flatMap(movies -> Mono.just(Optional.of(new ActorDetailsDto(actor, movies)))));
    }

    Mono<Actor> getActorResponse(String id) {

        return webClientBuilder
                .build()
                .get()
                .uri(actorDetailsUri, id)
                .retrieve()
                .bodyToMono(Actor.class);
    }

    Flux<Movie> getMovies(List<String> ids){
        return webClientBuilder
                .build()
                .post()
                .uri(moviesSearchUri)
                .body(Mono.just(ids), List.class)
                .retrieve()
                .bodyToFlux(Movie.class);
    }
}
