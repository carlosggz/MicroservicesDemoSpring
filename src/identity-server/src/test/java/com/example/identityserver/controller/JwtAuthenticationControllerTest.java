package com.example.identityserver.controller;

import com.example.identityserver.config.Constants;
import com.example.identityserver.models.JwtRequest;
import com.example.identityserver.objectmothers.UserObjectMother;
import com.example.identityserver.services.UsersService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
class JwtAuthenticationControllerTest {

    @MockBean
    UsersService usersService;

    @Autowired
    JwtAuthenticationController jwtController;

    MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(jwtController)
                .build();
    }
    @Test
    void getValidAuthReturnsToken() throws Exception {

        val expectedToken = "123";
        val mapper =  new ObjectMapper();
        val request = new JwtRequest(UserObjectMother.getRandomUsername(), UserObjectMother.getRandomPassword());
        val json = mapper.writeValueAsString(request);
        when(usersService.authenticateUser(request.getUsername(), request.getPassword())).thenReturn(expectedToken);

        mockMvc
                .perform(
                        post(Constants.AUTHENTICATE_PATH)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value(expectedToken));
    }
}