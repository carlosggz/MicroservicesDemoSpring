package com.example.moviesapi.application;

import com.example.moviesapi.domain.MoviesRepository;
import com.example.moviesapi.objectmothers.MoviesObjectMother;
import lombok.val;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@SpringBootTest
class GetMovieDetailsHandlerTest {

    @MockBean
    MoviesRepository repository;

    @Autowired
    GetMovieDetailsHandler handler;

    @Test
    void handleReturnNonNullResult() {

        val id = UUID.randomUUID().toString();
        when(repository.getById(id)).thenReturn(Optional.empty());

        val result = handler.handle(new GetMovieDetailsQuery(id));

        assertNotNull(result);
        assertTrue(result.isEmpty());
        then(repository).should(times(1)).getById(id);
    }

    @Test
    void handleReturnExistingResult() {

        val item = MoviesObjectMother.getRandomDomainEntity();
        when(repository.getById(item.getId())).thenReturn(Optional.of(item));

        val result = handler.handle(new GetMovieDetailsQuery(item.getId()));

        assertNotNull(result);
        assertTrue(result.isPresent());
        assertEquals(result.get(), item);
        then(repository).should(times(1)).getById(item.getId());
    }
}