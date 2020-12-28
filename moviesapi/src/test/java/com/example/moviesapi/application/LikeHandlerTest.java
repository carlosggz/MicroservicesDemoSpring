package com.example.moviesapi.application;

import com.example.moviesapi.domain.Movie;
import com.example.moviesapi.domain.MoviesRepository;
import com.example.moviesapi.objectmothers.MoviesObjectMother;
import lombok.val;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@SpringBootTest
class LikeHandlerTest {

    MoviesRepository repository;
    LikeHandler handler;

    @BeforeEach
    public void setUp()  {
        repository = mock(MoviesRepository.class);
        handler = new LikeHandler(repository);
    }

    @AfterEach
    public void cleanUp(){
        handler = null;
        repository = null;
    }

    @Test
    public void likeNonExistingItemThrowsException() {

        assertThrows(Exception.class, () -> {
            handler.handle(new LikeCommand(null));
        });

        assertFalse(handler.handle(new LikeCommand("")));
        assertFalse(handler.handle(new LikeCommand("123")));
    }

    @Test
    public void likeExistingItemIncrementByOne() throws Exception {

        val item = MoviesObjectMother.getRandomDomainEntity();
        val currentLikes = item.getLikes();
        when(repository.getById(item.getId())).thenReturn(Optional.of(item));
        ArgumentCaptor<Movie> captor = ArgumentCaptor.forClass(Movie.class);

        val result = handler.handle(new LikeCommand(item.getId()));

        assertTrue(result);
        then(repository).should(times(1)).getById(item.getId());
        then(repository).should(times(1)).save(any());
        verify(repository).save(captor.capture());
        assertEquals(currentLikes+1, captor.getValue().getLikes());
    }
}