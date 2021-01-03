package com.example.actorsapi.infrastructure;

import com.example.actorsapi.domain.Actor;
import com.example.actorsapi.domain.ActorDto;
import com.example.actorsapi.domain.ActorsRepository;
import com.example.actorsapi.infrastructure.jpa.ActorEntity;
import com.example.actorsapi.infrastructure.jpa.ActorsCrudRepository;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.val;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class ActorsRepositoryImpl implements ActorsRepository {

    private final ActorsCrudRepository actorsCrudRepository;

    @Override
    public List<ActorDto> getAll(){
        return actorsCrudRepository
                .findAll()
                .stream()
                .map(ActorMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Actor> getById(@NonNull String id) {

        return (id == null || id.trim().length() == 0)
                ? Optional.empty()
                : actorsCrudRepository
                .findById(id)
                .map(ActorMapper.INSTANCE::toDomain);
    }

    @Override
    public List<Actor> getByMovie(@NonNull String movieId) {

        return actorsCrudRepository
                .findByMovies_movieId(movieId)
                .stream()
                .map(ActorMapper.INSTANCE::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void save(@NonNull List<Actor> actors) {

        if (actors.size() == 0) {
            return;
        }

        List<ActorEntity> toSave = actors
                .stream()
                .map(x -> {
                    val dbActor = actorsCrudRepository.getOne(x.getId());
                    dbActor.setLikes(x.getLikes());
                    dbActor.setFirstName(x.getFirstName());
                    dbActor.setLastName(x.getLastName());
                    return dbActor;
                })
                .collect(Collectors.toList());

        actorsCrudRepository.saveAll(toSave);
    }
}
