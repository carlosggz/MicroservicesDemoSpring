package com.example.moviesapi.domain;

import lombok.NonNull;

import java.util.List;
import java.util.Optional;

public interface MoviesRepository {
    List<MovieDto> getAll();
    Optional<Movie> getById(@NonNull String id);
    void save(@NonNull Movie movie);
    List<MovieDto> getInList(@NonNull List<String> ids);
}
