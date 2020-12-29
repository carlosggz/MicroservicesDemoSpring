package com.example.actorsapi.infrastructure;

import com.example.actorsapi.domain.Actor;
import com.example.actorsapi.domain.ActorDto;
import com.example.actorsapi.domain.ActorsRepository;
import com.example.actorsapi.infrastructure.jpa.ActorsCrudRepository;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class ActorsRepositoryImpl implements ActorsRepository {

    private final ActorsCrudRepository repository;

    @Override
    public List<ActorDto> getAll(){
        return repository
                .findAll()
                .stream()
                .map(ActorMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Actor> getById(@NonNull String id) {

        return (id == null || id.trim().length() == 0)
                ? Optional.empty()
                : repository
                .findById(id)
                .map(ActorMapper.INSTANCE::toDomain);
    }
}
