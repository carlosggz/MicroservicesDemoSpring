package com.example.moviesapi.application;

import an.awesome.pipelinr.Command;
import com.example.moviesapi.domain.MovieDto;
import com.example.moviesapi.domain.MoviesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
public class GetMoviesInListHandler implements Command.Handler<GetMoviesInListQuery, List<MovieDto>> {

    final MoviesRepository repository;

    @Override
    public List<MovieDto> handle(GetMoviesInListQuery command) {
        return command.ids.size() == 0 ? new ArrayList<>() : repository.getInList(command.ids);
    }
}
