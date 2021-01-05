package com.example.gatewayapi.controllers;

import com.example.gatewayapi.domain.ActorDetailsDto;
import com.example.gatewayapi.services.ActorDetailsService;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.concurrent.ExecutionException;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/agg")
public class AggregationsController {

    ActorDetailsService actorDetailsService;

    @GetMapping("/actor/{id}")
    public Mono<ResponseEntity<ActorDetailsDto>> getActorDetails(
            @PathVariable String id,
            @RequestHeader("Authorization") String authorization) throws ExecutionException, InterruptedException {

        return actorDetailsService
                .getActor(id, authorization)
                .flatMap(details -> {
                    val toReturn = new ResponseEntity<ActorDetailsDto>(details.isEmpty() ? null : details.get(), details.isEmpty() ? HttpStatus.NOT_FOUND : HttpStatus.OK);
                    return Mono.just(toReturn);
                });
    }
}
