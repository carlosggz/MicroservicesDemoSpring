package com.example.actorsapi.application;

import an.awesome.pipelinr.Command;
import com.example.actorsapi.domain.Actor;
import com.example.actorsapi.domain.ActorsRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Optional;

@AllArgsConstructor
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class GetActorDetailsHandler implements Command.Handler<GetActorDetailsQuery, Optional<Actor>> {

    ActorsRepository repository;

    @Override
    public Optional<Actor> handle(GetActorDetailsQuery command) {
        return repository.getById(command.id);
    }
}
