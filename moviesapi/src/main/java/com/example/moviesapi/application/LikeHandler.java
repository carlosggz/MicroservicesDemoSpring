package com.example.moviesapi.application;

import an.awesome.pipelinr.Command;
import an.awesome.pipelinr.Voidy;
import com.example.moviesapi.domain.MoviesRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class LikeHandler implements Command.Handler<LikeCommand, Boolean> {

    MoviesRepository repository;

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

        return true;
    }
}
