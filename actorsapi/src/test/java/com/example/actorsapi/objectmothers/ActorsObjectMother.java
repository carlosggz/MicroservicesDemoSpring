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

    public static ActorDto getRandomDto() {
        return new ActorDto(UUID.randomUUID().toString(), faker.name().fullName());
    }

    private static MovieEntity getRandomMovieEntity(ActorEntity actor) {
        return new MovieEntity(UUID.randomUUID().toString(), "M" + faker.random().nextInt(1, 10).toString(), faker.name().title(), actor);
    }
}
