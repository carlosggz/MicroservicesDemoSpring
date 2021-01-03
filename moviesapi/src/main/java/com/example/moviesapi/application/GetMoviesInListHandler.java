package com.example.moviesapi.application;

import an.awesome.pipelinr.Command;
import com.example.moviesapi.domain.MovieDto;
import com.example.moviesapi.domain.MoviesRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class GetMoviesInListHandler implements Command.Handler<GetMoviesInListQuery, List<MovieDto>> {

    MoviesRepository repository;

    @Override
    public List<MovieDto> handle(GetMoviesInListQuery command) {
        return command.ids.size() == 0 ? new ArrayList<>() : repository.getInList(command.ids);
    }
}
