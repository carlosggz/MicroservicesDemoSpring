package com.example.moviesapi.domain;

import java.util.List;
import java.util.Optional;

public interface MoviesRepository {
    public List<MovieDto> getAll();
    Optional<Movie> getById(String id);
    void like(String id) throws Exception;
}
