package com.example.moviesapi.application;

import an.awesome.pipelinr.Command;
import com.example.moviesapi.config.Constants;
import com.example.moviesapi.domain.LikeMovieDomainEvent;
import com.example.moviesapi.domain.MoviesRepository;
import com.example.shared.domain.EventBus;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class LikeHandler implements Command.Handler<LikeCommand, Boolean> {

    MoviesRepository repository;
    EventBus eventBus;

    @Override
    public Boolean handle(LikeCommand command)  {

        if (command.id == null || command.id.trim().length() == 0)
            return false;

        var value = repository.getById(command.id);

        if (value.isEmpty())
            return false;

        var entity = value.get();
        entity.setLikes(entity.getLikes()+1);
        repository.save(entity);

        eventBus.publishToQueue(List.of(new LikeMovieDomainEvent(Constants.LIKES_QUEUE, entity.getId(), entity.getLikes())));

        return true;
    }
}
