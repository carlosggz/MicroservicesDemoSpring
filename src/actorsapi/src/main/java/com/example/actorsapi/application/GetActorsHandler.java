package com.example.actorsapi.application;

import an.awesome.pipelinr.Command;
import com.example.actorsapi.domain.ActorDto;
import com.example.actorsapi.domain.ActorsRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class GetActorsHandler implements Command.Handler<GetActorsQuery, List<ActorDto>> {

    ActorsRepository repository;

    @Override
    public List<ActorDto> handle(GetActorsQuery command) {
        return repository.getAll();
    }
}
