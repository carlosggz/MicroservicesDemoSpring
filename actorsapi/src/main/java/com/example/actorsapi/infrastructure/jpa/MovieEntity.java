package com.example.actorsapi.infrastructure.jpa;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Builder
@AllArgsConstructor
@ToString
@Table(name = "movie")
public class MovieEntity implements Serializable  {

    @Id
    @Column(name = "id", length = 50, columnDefinition = "varchar(50)", updatable = false, nullable = false )
    private String id;

    @Column(name = "movie_id", length = 50, columnDefinition = "varchar(50)", updatable = false, nullable = false )
    private String movieId;

    @ManyToOne
    @JoinColumn(name = "actor_id")
    private ActorEntity actor;
}
