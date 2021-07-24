package com.example.actorsapi.application;

import an.awesome.pipelinr.Command;
import com.example.actorsapi.domain.ActorDto;
import com.example.actorsapi.domain.ActorsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class GetActorsHandler implements Command.Handler<GetActorsQuery, List<ActorDto>> {

    final ActorsRepository repository;

    @Override
    public List<ActorDto> handle(GetActorsQuery command) {
        return repository.getAll();
    }
}
