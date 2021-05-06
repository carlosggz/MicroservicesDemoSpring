package com.example.moviesapi.application;

import com.example.moviesapi.domain.LikeMovieDomainEvent;
import com.example.moviesapi.domain.Movie;
import com.example.moviesapi.domain.MoviesRepository;
import com.example.moviesapi.objectmothers.MoviesObjectMother;
import com.example.shared.domain.EventBus;
import lombok.val;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LikeHandlerTest {

    @Mock
    MoviesRepository repository;

    @Mock
    EventBus eventBus;

    @InjectMocks
    LikeHandler handler;

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
        ArgumentCaptor<Movie> captorRepo = ArgumentCaptor.forClass(Movie.class);
        ArgumentCaptor<List<LikeMovieDomainEvent>> captorEvent = ArgumentCaptor.forClass(List.class);

        val result = handler.handle(new LikeCommand(item.getId()));

        assertTrue(result);
        then(repository).should(times(1)).getById(item.getId());
        then(repository).should(times(1)).save(any());
        then(eventBus).should(times(1)).publishToQueue(any());
        verify(repository).save(captorRepo.capture());
        assertEquals(currentLikes+1, captorRepo.getValue().getLikes());
        verify(eventBus).publishToQueue(captorEvent.capture());
        assertEquals(1, captorEvent.getValue().size());
        assertEquals(item.getId(), captorEvent.getValue().get(0).getAggregateRootId());
    }
}