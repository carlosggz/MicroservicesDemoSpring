package com.example.actorsapi.application;

import com.example.actorsapi.domain.ActorsRepository;
import com.example.actorsapi.objectmothers.ActorsObjectMother;
import lombok.val;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@SpringBootTest
class GetActorsHandlerTest {

    ActorsRepository repository;
    GetActorsHandler handler;

    @BeforeEach
    public void setUp()  {
        repository = mock(ActorsRepository.class);
        handler = new GetActorsHandler(repository);
    }

    @AfterEach
    public void cleanUp(){
        handler = null;
        repository = null;
    }

    @Test
    void handleReturnNonNullResult() {

        when(repository.getAll()).thenReturn(new ArrayList<>());

        val result = handler.handle(new GetActorsQuery());

        assertNotNull(result);
        then(repository).should(times(1)).getAll();
    }

    @Test
    void handleReturnAllMovies() {

        val items = List.of(
                ActorsObjectMother.getRandomDto(),
                ActorsObjectMother.getRandomDto(),
                ActorsObjectMother.getRandomDto()
        );
        when(repository.getAll()).thenReturn(items);

        val result = handler.handle(new GetActorsQuery());

        assertNotNull(result);
        assertEquals(items.size(), result.size());
        items.forEach(x -> assertTrue(result.stream().anyMatch(x::equals)));
        then(repository).should(times(1)).getAll();
    }
}