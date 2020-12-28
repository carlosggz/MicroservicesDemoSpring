package com.example.moviesapi.application;

import an.awesome.pipelinr.Command;
import com.example.moviesapi.domain.Movie;
import com.example.moviesapi.domain.MoviesRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import java.util.Optional;

@AllArgsConstructor
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class GetMovieDetailsHandler implements Command.Handler<GetMovieDetailsQuery, Optional<Movie>> {

    MoviesRepository repository;

    @Override
    public Optional<Movie> handle(GetMovieDetailsQuery command) {
        return repository.getById(command.id);
    }
}
