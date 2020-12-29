package com.example.actorsapi.application;

import an.awesome.pipelinr.Command;
import com.example.actorsapi.domain.Actor;
import lombok.AllArgsConstructor;
import lombok.NonNull;

import java.util.Optional;

@AllArgsConstructor
public class GetActorDetailsQuery implements Command<Optional<Actor>> {

    @NonNull
    String id;
}
