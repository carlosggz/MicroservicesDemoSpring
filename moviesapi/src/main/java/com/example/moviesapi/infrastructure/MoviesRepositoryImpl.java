package com.example.moviesapi.infrastructure;

import com.example.moviesapi.domain.Movie;
import com.example.moviesapi.domain.MovieDto;
import com.example.moviesapi.domain.MoviesRepository;
import com.example.moviesapi.infrastructure.jpa.MoviesCrudRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class MoviesRepositoryImpl implements MoviesRepository {

    private final MoviesCrudRepository repository;

    @Override
    public List<MovieDto> getAll() {
        return repository
                .findAll()
                .stream()
                .map(MovieMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Movie> getById(String id) {

        return (id == null || id.trim().length() == 0)
                ? Optional.empty()
                : repository
                    .findById(id)
                    .map(MovieMapper.INSTANCE::toDomain);
    }

    @Override
    public void like(String id) throws Exception {

        if (id == null || id.trim().length() == 0)
            throw new Exception("Invalid id");

        var entity = repository.findById(id);

        if (entity.isEmpty())
            throw new Exception("Invalid id");

        var movie = entity.get();
        movie.setLikes(movie.getLikes()+1);
        repository.save(movie);
    }
}
