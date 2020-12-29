package com.example.actorsapi.infrastructure.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActorsCrudRepository extends JpaRepository<ActorEntity, String> {
}
