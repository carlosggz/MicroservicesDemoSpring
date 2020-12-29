package com.example.actorsapi.domain;

import lombok.NonNull;

import java.util.List;
import java.util.Optional;

public interface ActorsRepository {
    public List<ActorDto> getAll();
    Optional<Actor> getById(@NonNull String id);
}
