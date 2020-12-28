package com.example.moviesapi.infrastructure.jpa;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
@Entity
public class MovieEntity {

    @Id
    private String id;

    @Column(length = 255, columnDefinition = "varchar(255)", updatable = true, nullable = false )
    private String title;

    private int likes;
}
