package com.example.moviesapi.controllers;

import an.awesome.pipelinr.Pipeline;
import com.example.moviesapi.application.GetMovieDetailsQuery;
import com.example.moviesapi.application.GetMoviesInListQuery;
import com.example.moviesapi.application.GetMoviesQuery;
import com.example.moviesapi.application.LikeCommand;
import com.example.moviesapi.config.Constants;
import com.example.moviesapi.objectmothers.MoviesObjectMother;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.val;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class MoviesControllerTest {

    @Mock
    Pipeline pipeline;

    @InjectMocks
    MoviesController moviesController;

    MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(moviesController)
                .build();
    }

    @Test
    void getAllMoviesReturnsTheMoviesList() throws Exception {

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

    @Test
    void getInvalidMovieReturns404() throws Exception {

        val entity = MoviesObjectMother.getRandomEntity();
        when(pipeline.send(any(GetMovieDetailsQuery.class))).thenReturn(Optional.empty());

        mockMvc
                .perform(get(Constants.MOVIES_BASE_URL + "/" + entity.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void getValidMovieReturnsTheDetails() throws Exception {

        val entity = MoviesObjectMother.getRandomDomainEntity();

        when(pipeline.send(any(GetMovieDetailsQuery.class))).thenReturn(Optional.of(entity));

        mockMvc
                .perform(get(Constants.MOVIES_BASE_URL + "/" + entity.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(entity.getId())));
    }

    @Test
    void likeInvalidMovieReturns404() throws Exception {

        val entity = MoviesObjectMother.getRandomEntity();
        when(pipeline.send(any(LikeCommand.class))).thenReturn(false);

        mockMvc
                .perform(post(Constants.MOVIES_BASE_URL + "/" + entity.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    void likeValidMovieReturnsOk() throws Exception {

        val entity = MoviesObjectMother.getRandomEntity();
        when(pipeline.send(any(LikeCommand.class))).thenReturn(true);

        mockMvc
                .perform(post(Constants.MOVIES_BASE_URL + "/" + entity.getId()))
                .andExpect(status().isOk());
    }

    @Test
    void getMoviesInListReturnsTheList() throws Exception {

        val items = List.of(
                MoviesObjectMother.getRandomDto(),
                MoviesObjectMother.getRandomDto(),
                MoviesObjectMother.getRandomDto()
        );
        when(pipeline.send(any(GetMoviesInListQuery.class))).thenReturn(items);
        val ids = List.of(
                items.get(0).getId(),
                items.get(1).getId(),
                items.get(2).getId()
        );
        val mapper = new ObjectMapper();
        val json = mapper.writeValueAsString(ids);

        mockMvc
                .perform(post(Constants.MOVIES_BASE_URL + "/search")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(items.size())));
    }
}