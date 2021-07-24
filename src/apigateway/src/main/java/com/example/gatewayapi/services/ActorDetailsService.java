package com.example.gatewayapi.services;

import com.example.gatewayapi.domain.ActorDetailsDto;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.concurrent.ExecutionException;

public interface ActorDetailsService {

    Optional<ActorDetailsDto> getActor(String id, String authorization) throws ExecutionException, InterruptedException;
}
