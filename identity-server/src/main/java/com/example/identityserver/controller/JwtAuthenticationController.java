package com.example.identityserver.controller;

import com.example.identityserver.config.Constants;
import com.example.identityserver.models.JwtRequest;
import com.example.identityserver.models.JwtResponse;
import com.example.identityserver.services.UsersService;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@AllArgsConstructor
public class JwtAuthenticationController {

    private final UsersService usersService;

    @PostMapping(value = Constants.AUTHENTICATE_PATH)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

        val token = usersService.authenticateUser(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        return ResponseEntity.ok(new JwtResponse(token));
    }
}