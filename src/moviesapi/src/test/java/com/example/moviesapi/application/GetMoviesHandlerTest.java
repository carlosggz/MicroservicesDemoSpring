package com.example.moviesapi.application;

import com.example.moviesapi.domain.MovieDto;
import com.example.moviesapi.domain.MoviesRepository;
import com.example.moviesapi.infrastructure.jpa.MovieEntity;
import com.example.moviesapi.objectmothers.MoviesObjectMother;
import lombok.val;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetMoviesHandlerTest {

    @Mock
    MoviesRepository repository;

    @InjectMocks
    GetMoviesHandler handler;

    @Test
    void handleReturnNonNullResult() {

        when(repository.getAll()).thenReturn(new ArrayList<>());

        val result = handler.handle(new GetMoviesQuery());

        assertNotNull(result);
        then(repository).should(times(1)).getAll();
    }

    @Test
    void handleReturnAllMovies() {

        val items = List.of(
                MoviesObjectMother.getRandomDto(),
                MoviesObjectMother.getRandomDto(),
                MoviesObjectMother.getRandomDto()
        );
        when(repository.getAll()).thenReturn(items);

        val result = handler.handle(new GetMoviesQuery());

        assertNotNull(result);
        assertEquals(items.size(), result.size());
        items.forEach(x -> assertTrue(result.stream().anyMatch(x::equals)));
        then(repository).should(times(1)).getAll();
    }
}