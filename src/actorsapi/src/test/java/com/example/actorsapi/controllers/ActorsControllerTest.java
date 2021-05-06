package com.example.actorsapi.controllers;

import an.awesome.pipelinr.Pipeline;
import com.example.actorsapi.application.GetActorDetailsQuery;
import com.example.actorsapi.application.GetActorsQuery;
import com.example.actorsapi.config.Constants;
import com.example.actorsapi.objectmothers.ActorsObjectMother;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ActorsControllerTest {

    @Mock
    Pipeline pipeline;

    @InjectMocks
    ActorsController actorsController;

    MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(actorsController)
                .build();
    }

    @Test
    void getAllMovies() throws Exception {

        val items = List.of(
                ActorsObjectMother.getRandomDto(),
                ActorsObjectMother.getRandomDto(),
                ActorsObjectMother.getRandomDto()
        );
        when(pipeline.send(any(GetActorsQuery.class))).thenReturn(items);

        mockMvc
                .perform(get(Constants.ACTORS_BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(items.size())));
    }

    @Test
    void getInvalidMovieReturns404() throws Exception {

        val entity = ActorsObjectMother.getRandomEntity();
        when(pipeline.send(any(GetActorDetailsQuery.class))).thenReturn(Optional.empty());

        mockMvc
                .perform(get(Constants.ACTORS_BASE_URL + "/" + entity.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void getValidMovieReturnsTheDetails() throws Exception {

        val entity = ActorsObjectMother.getRandomDomainEntity();

        when(pipeline.send(any(GetActorDetailsQuery.class))).thenReturn(Optional.of(entity));

        mockMvc
                .perform(get(Constants.ACTORS_BASE_URL + "/" + entity.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(entity.getId())));
    }
}