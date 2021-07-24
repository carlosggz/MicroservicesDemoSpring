package com.example.moviesapi.application;

import an.awesome.pipelinr.Command;
import com.example.moviesapi.domain.MovieDto;
import com.example.moviesapi.domain.MoviesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;


@RequiredArgsConstructor
@Component
public class GetMoviesHandler implements Command.Handler<GetMoviesQuery, List<MovieDto>> {

    final MoviesRepository repository;

    @Override
    public List<MovieDto> handle(GetMoviesQuery command) {
        return repository.getAll();
    }
}
