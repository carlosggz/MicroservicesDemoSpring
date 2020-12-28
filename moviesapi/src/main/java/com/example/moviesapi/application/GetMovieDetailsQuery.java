package com.example.moviesapi.application;

import an.awesome.pipelinr.Command;
import com.example.moviesapi.domain.Movie;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.util.Optional;

@AllArgsConstructor
public class GetMovieDetailsQuery implements Command<Optional<Movie>> {

    @NonNull
    String id;
}
