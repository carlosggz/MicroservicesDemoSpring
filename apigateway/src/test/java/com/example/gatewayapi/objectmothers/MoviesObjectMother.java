package com.example.gatewayapi.objectmothers;

import com.example.gatewayapi.domain.Movie;
import com.github.javafaker.Faker;

public final class MoviesObjectMother {

    static final Faker faker = new Faker();

    public static Movie getRandom() {
        return new Movie(String.valueOf(faker.random().nextInt(100)), faker.funnyName().name());
    }
}
