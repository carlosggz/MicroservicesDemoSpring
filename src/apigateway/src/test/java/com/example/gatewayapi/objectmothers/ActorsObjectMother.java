package com.example.gatewayapi.objectmothers;

import com.example.gatewayapi.domain.Actor;
import com.github.javafaker.Faker;

import java.util.HashSet;
import java.util.List;

public final class ActorsObjectMother {
    static final Faker faker = new Faker();

    public static Actor getRandom(List<String> movies) {
        return new Actor(
                String.valueOf(faker.random().nextInt(100)),
                faker.name().firstName(),
                faker.name().lastName(),
                faker.random().nextInt(100),
                new HashSet<>(movies));
    }
}
