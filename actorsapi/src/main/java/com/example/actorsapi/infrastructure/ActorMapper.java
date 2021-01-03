package com.example.actorsapi.infrastructure;

import com.example.actorsapi.domain.Actor;
import com.example.actorsapi.domain.ActorDto;
import com.example.actorsapi.infrastructure.jpa.ActorEntity;
import com.example.actorsapi.infrastructure.jpa.MovieEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ActorMapper {

    ActorMapper INSTANCE = Mappers.getMapper(ActorMapper.class);

    default ActorDto toDto(ActorEntity entity) {
        return new ActorDto(
                entity.getId(),
                entity.getFirstName() + " " + entity.getLastName()
        );
    }

    default Actor toDomain(ActorEntity actorEntity) {
        return new Actor(
                actorEntity.getId(),
                actorEntity.getFirstName(),
                actorEntity.getLastName(),
                actorEntity.getLikes(),
                actorEntity.getMovies().stream().map(MovieEntity::getMovieId).collect(Collectors.toSet())
        );
    }
}