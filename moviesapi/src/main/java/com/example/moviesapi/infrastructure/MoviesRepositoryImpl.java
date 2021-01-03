package com.example.moviesapi.infrastructure;

import com.example.moviesapi.domain.Movie;
import com.example.moviesapi.domain.MovieDto;
import com.example.moviesapi.domain.MoviesRepository;
import com.example.moviesapi.infrastructure.jpa.MoviesCrudRepository;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.val;
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
    public Optional<Movie> getById(@NonNull String id) {

        return (id == null || id.trim().length() == 0)
                ? Optional.empty()
                : repository
                    .findById(id)
                    .map(MovieMapper.INSTANCE::toDomain);
    }

    @Override
    public void save(@NonNull Movie movie){

        val entity = MovieMapper.INSTANCE.toEntity(movie);
        repository.save(entity);
    }

    @Override
    public List<MovieDto> getInList(@NonNull List<String> ids) {
        return ids.size() == 0
                ? new ArrayList<>()
                : repository
                    .findAllById(ids)
                    .stream()
                    .map(MovieMapper.INSTANCE::toDto)
                    .collect(Collectors.toList());
    }
}
