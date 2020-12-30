package com.example.actorsapi.infrastructure.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActorsCrudRepository extends JpaRepository<ActorEntity, String> {
    List<ActorEntity> findByMovies_reference(String movieId);
}
