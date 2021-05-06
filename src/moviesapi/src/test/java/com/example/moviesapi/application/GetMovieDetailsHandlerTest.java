package com.example.moviesapi.application;

import com.example.moviesapi.domain.MoviesRepository;
import com.example.moviesapi.objectmothers.MoviesObjectMother;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetMovieDetailsHandlerTest {

    @Mock
    MoviesRepository repository;

    @InjectMocks
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