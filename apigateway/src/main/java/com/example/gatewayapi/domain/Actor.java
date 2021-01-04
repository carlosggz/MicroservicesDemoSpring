package com.example.gatewayapi.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Actor {
    String id;
    String firstName;
    String lastName;
    int likes;
    Set<String> movies;
}

