package com.example.actorsapi.objectmothers;

import com.example.actorsapi.domain.Actor;
import com.example.actorsapi.domain.ActorDto;
import com.example.actorsapi.infrastructure.jpa.ActorEntity;
import com.example.actorsapi.infrastructure.jpa.MovieEntity;
import com.github.javafaker.Faker;
import lombok.val;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public final class ActorsObjectMother {

    static Faker faker = new Faker();

    public static Actor getRandomDomainEntity() {
        return new Actor(UUID.randomUUID().toString(), faker.name().firstName(), faker.name().lastName(), 0, Set.of(faker.name().title()));
    }

    public static ActorEntity getRandomEntity() {
        val actor = new ActorEntity(UUID.randomUUID().toString(), faker.name().firstName(), faker.name().lastName(), 0, null);
        val movies = new HashSet<MovieEntity>();
        movies.add(getRandomMovieEntity(actor));
        actor.setMovies(movies);
        return actor;
    }

    public static ActorEntity getRandomEntity(String movieId){
        val entity = ActorsObjectMother.getRandomEntity();
        entity.getMovies().stream().findFirst().get().setMovieId(movieId);
        return entity;
    }

    public static ActorDto getRandomDto() {
        return new ActorDto(UUID.randomUUID().toString(), faker.name().fullName());
    }

    public static Actor getActorWithMovie(String movieId, int likes){
        val actor = ActorsObjectMother.getRandomDomainEntity();
        val movies = new HashSet<String>(actor.getMovies());
        movies.add(movieId);
        actor.setMovies(movies);
        actor.setLikes(likes);
        return actor;
    }

    private static MovieEntity getRandomMovieEntity(ActorEntity actor) {
        return new MovieEntity(UUID.randomUUID().toString(), "M" + faker.random().nextInt(1, 10).toString(), actor);
    }
}
