package com.example.actorsapi.domain;

import lombok.NonNull;

import java.util.List;
import java.util.Optional;

public interface ActorsRepository {
    List<ActorDto> getAll();
    Optional<Actor> getById(@NonNull String id);
    List<Actor> getByMovie(@NonNull String movieId);
    void save(@NonNull List<Actor> actors);
}
