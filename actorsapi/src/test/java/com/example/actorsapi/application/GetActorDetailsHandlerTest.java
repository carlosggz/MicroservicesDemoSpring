package com.example.actorsapi.application;

import com.example.actorsapi.domain.ActorsRepository;
import com.example.actorsapi.objectmothers.ActorsObjectMother;
import lombok.val;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@SpringBootTest
class GetActorDetailsHandlerTest {

    @MockBean
    ActorsRepository repository;

    @Autowired
    GetActorDetailsHandler handler;

    @Test
    void handleReturnNonNullResult() {

        val id = UUID.randomUUID().toString();
        when(repository.getById(id)).thenReturn(Optional.empty());

        val result = handler.handle(new GetActorDetailsQuery(id));

        assertNotNull(result);
        assertTrue(result.isEmpty());
        then(repository).should(times(1)).getById(id);
    }

    @Test
    void handleReturnExistingResult() {

        val item = ActorsObjectMother.getRandomDomainEntity();
        when(repository.getById(item.getId())).thenReturn(Optional.of(item));

        val result = handler.handle(new GetActorDetailsQuery(item.getId()));

        assertNotNull(result);
        assertTrue(result.isPresent());
        assertEquals(result.get(), item);
        then(repository).should(times(1)).getById(item.getId());
    }
}