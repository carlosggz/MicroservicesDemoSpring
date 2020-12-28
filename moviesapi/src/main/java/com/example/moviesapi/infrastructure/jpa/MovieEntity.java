package com.example.moviesapi.infrastructure.jpa;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
@Entity
@Table(name = "movie")
public class MovieEntity {

    @Id
    @Column(length = 50, columnDefinition = "varchar(50)", updatable = false, nullable = false )
    private String id;

    @Column(length = 255, columnDefinition = "varchar(255)", updatable = true, nullable = false )
    private String title;

    private int likes;
}
