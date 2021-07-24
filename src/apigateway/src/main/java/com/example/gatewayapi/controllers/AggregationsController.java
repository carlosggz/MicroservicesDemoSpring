package com.example.gatewayapi.controllers;

import com.example.gatewayapi.domain.ActorDetailsDto;
import com.example.gatewayapi.services.ActorDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.concurrent.ExecutionException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/agg")
public class AggregationsController {

    final ActorDetailsService actorDetailsService;

    @GetMapping("/actor/{id}")
    public Mono<ResponseEntity<ActorDetailsDto>> getActorDetails(
            @PathVariable String id,
            @RequestHeader("Authorization") String authorization) throws ExecutionException, InterruptedException {

        return Mono.fromCallable(() -> actorDetailsService.getActor(id, authorization))
                .flatMap(details -> {
                    val toReturn = new ResponseEntity<ActorDetailsDto>(details.isEmpty() ? null : details.get(), details.isEmpty() ? HttpStatus.NOT_FOUND : HttpStatus.OK);
                    return Mono.just(toReturn);
                });
    }
}
