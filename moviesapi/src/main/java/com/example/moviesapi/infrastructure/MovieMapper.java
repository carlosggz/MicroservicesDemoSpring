package com.example.moviesapi.infrastructure;

import com.example.moviesapi.domain.Movie;
import com.example.moviesapi.domain.MovieDto;
import com.example.moviesapi.infrastructure.jpa.MovieEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface MovieMapper {

    MovieMapper INSTANCE = Mappers.getMapper(MovieMapper.class);

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "title", target = "title")
    })
    MovieDto toDto(MovieEntity entity);

    Movie toDomain(MovieEntity entity);
}
