package com.example.moviesapi.controllers;

import an.awesome.pipelinr.Pipeline;
import com.example.moviesapi.application.GetMoviesQuery;
import com.example.moviesapi.config.Constants;
import com.example.moviesapi.objectmothers.MoviesObjectMother;
import lombok.val;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class MoviesControllerTest {

    Pipeline pipeline;
    MoviesController moviesController;
    MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        pipeline = mock(Pipeline.class);
        moviesController = new MoviesController(pipeline);

        mockMvc = MockMvcBuilders
                .standaloneSetup(moviesController)
                .build();
    }

    @Test
    void getAllMovies() throws Exception {

        val items = List.of(
                MoviesObjectMother.getRandomDto(),
                MoviesObjectMother.getRandomDto(),
                MoviesObjectMother.getRandomDto()
        );
        when(pipeline.send(any(GetMoviesQuery.class))).thenReturn(items);

        mockMvc
                .perform(get(Constants.MOVIES_BASE_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(items.size())));
    }
}