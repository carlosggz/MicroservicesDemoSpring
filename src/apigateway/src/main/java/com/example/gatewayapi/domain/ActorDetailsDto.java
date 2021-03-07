package com.example.gatewayapi.domain;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Value;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@EqualsAndHashCode
public class ActorDetailsDto {

    String id;
    String firstName;
    String lastName;
    int likes;
    Set<Movie> movies;

    public ActorDetailsDto(Actor actor, List<Movie> movies) {
        this.id = actor.getId();
        this.lastName = actor.getLastName();
        this.firstName = actor.getFirstName();
        this.likes = actor.getLikes();
        this.movies = new HashSet<>(movies);
    }
}
