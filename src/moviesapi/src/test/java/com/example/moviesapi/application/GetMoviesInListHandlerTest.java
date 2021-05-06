package com.example.moviesapi.application;

import com.example.moviesapi.domain.MoviesRepository;
import com.example.moviesapi.objectmothers.MoviesObjectMother;
import lombok.val;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetMoviesInListHandlerTest {

    @Mock
    MoviesRepository repository;

    @InjectMocks
    GetMoviesInListHandler handler;

    @Test
    void handleEmptyListReturnEmptyList() {

        val ids = new ArrayList<String>();

        val result = handler.handle(new GetMoviesInListQuery(ids));

        assertNotNull(result);
        assertTrue(result.isEmpty());
        then(repository).should(times(0)).getInList(any());
    }

    @Test
    void handleInvalidIdsReturnEmptyResult() {

        val ids = List.of("1", "2");
        when(repository.getInList(ids)).thenReturn(new ArrayList<>());

        val result = handler.handle(new GetMoviesInListQuery(ids));

        assertNotNull(result);
        assertTrue(result.isEmpty());
        then(repository).should(times(1)).getInList(ids);
    }

    @Test
    void handleReturnExistingResult() {

        val items = List.of(MoviesObjectMother.getRandomDto(), MoviesObjectMother.getRandomDto());
        val ids = List.of(items.get(0).getId(), items.get(1).getId());
        when(repository.getInList(ids)).thenReturn(items);

        val result = handler.handle(new GetMoviesInListQuery(ids));

        assertNotNull(result);
        assertEquals(items.size(), result.size());
        items.forEach(x -> assertTrue(result.stream().anyMatch(x::equals)));
        then(repository).should(times(1)).getInList(ids);
    }

}