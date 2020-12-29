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
@ToString(exclude = {"actor", "id", "reference"})
@Table(name = "movie")
public class MovieEntity implements Serializable  {

    @Id
    @Column(length = 50, columnDefinition = "varchar(50)", updatable = false, nullable = false )
    private String id;

    @Column(length = 50, columnDefinition = "varchar(50)", updatable = false, nullable = false )
    private String reference;

    @Column(length = 255, columnDefinition = "varchar(100)", updatable = true, nullable = false )
    private String title;

    @ManyToOne
    @JoinColumn(name = "actor_id")
    private ActorEntity actor;
}
