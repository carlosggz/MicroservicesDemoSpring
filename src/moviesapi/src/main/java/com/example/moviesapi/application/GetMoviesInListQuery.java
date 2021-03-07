package com.example.moviesapi.application;

import an.awesome.pipelinr.Command;
import com.example.moviesapi.domain.MovieDto;
import lombok.AllArgsConstructor;
import lombok.NonNull;

import java.util.List;

@AllArgsConstructor
public class GetMoviesInListQuery implements Command<List<MovieDto>> {

    @NonNull
    List<String> ids;
}
