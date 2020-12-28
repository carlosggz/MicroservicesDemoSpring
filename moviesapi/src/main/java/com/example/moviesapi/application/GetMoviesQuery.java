package com.example.moviesapi.application;

import an.awesome.pipelinr.Command;
import com.example.moviesapi.domain.MovieDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetMoviesQuery implements Command<List<MovieDto>> {
}
