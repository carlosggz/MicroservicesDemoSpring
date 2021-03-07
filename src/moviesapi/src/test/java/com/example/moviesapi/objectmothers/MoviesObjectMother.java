package com.example.moviesapi.objectmothers;

import com.example.moviesapi.domain.Movie;
import com.example.moviesapi.domain.MovieDto;
import com.example.moviesapi.infrastructure.jpa.MovieEntity;
import com.github.javafaker.Faker;
import lombok.val;

import java.util.Random;
import java.util.UUID;

public final class MoviesObjectMother {

    static Faker faker = new Faker();

    public static Movie getRandomDomainEntity() {
        return new Movie(UUID.randomUUID().toString(), faker.name().title(), faker.random().nextInt(0, 100));
    }

    public static MovieEntity getRandomEntity() {
        return new MovieEntity(UUID.randomUUID().toString(), faker.name().title(), faker.random().nextInt(0, 100));
    }

    public static MovieDto getRandomDto() {
        return new MovieDto(UUID.randomUUID().toString(), faker.name().title());
    }
}
