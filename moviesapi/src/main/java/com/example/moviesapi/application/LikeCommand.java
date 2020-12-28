package com.example.moviesapi.application;

import an.awesome.pipelinr.Command;
import an.awesome.pipelinr.Voidy;
import com.example.moviesapi.domain.Movie;
import lombok.AllArgsConstructor;
import lombok.NonNull;

import java.util.Optional;

@AllArgsConstructor
public class LikeCommand implements Command<Boolean> {

    @NonNull
    String id;
}
