package com.example.moviesapi.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Movie {

    private String id;
    private String title;
    private int likes;
}
