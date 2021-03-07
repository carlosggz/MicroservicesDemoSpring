package com.example.actorsapi.infrastructure.jpa;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Builder
@AllArgsConstructor
@ToString(exclude = "movies")
@Table(name = "actor")
public class ActorEntity implements Serializable {

    @Id
    @Column(length = 50, columnDefinition = "varchar(50)", updatable = false, nullable = false )
    private String id;

    @Column(length = 100, columnDefinition = "varchar(100)", updatable = true, nullable = false )
    private String firstName;

    @Column(length = 100, columnDefinition = "varchar(100)", updatable = true, nullable = false )
    String lastName;

    int likes;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "actor")
    Set<MovieEntity> movies = new HashSet<>();
}