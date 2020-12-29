package com.example.actorsapi.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class Actor {
    String id;
    String firstName;
    String lastName;
    int likes;
    Set<String> movies;

    public String getFullName() {
        return firstName + " " + lastName;
    }
}
