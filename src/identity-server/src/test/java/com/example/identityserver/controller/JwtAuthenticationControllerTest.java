package com.example.identityserver.controller;

import com.example.identityserver.config.Constants;
import com.example.identityserver.models.JwtRequest;
import com.example.identityserver.objectmothers.UserObjectMother;
import com.example.identityserver.services.UsersService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class JwtAuthenticationControllerTest {

    @Mock
    UsersService usersService;

    @InjectMocks
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