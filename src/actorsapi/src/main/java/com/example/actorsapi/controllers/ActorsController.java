package com.example.actorsapi.controllers;

import an.awesome.pipelinr.Pipeline;
import com.example.actorsapi.application.GetActorDetailsQuery;
import com.example.actorsapi.application.GetActorsQuery;
import com.example.actorsapi.config.Constants;
import com.example.actorsapi.domain.Actor;
import com.example.actorsapi.domain.ActorDto;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(Constants.ACTORS_BASE_URL)
public class ActorsController {

    private final Pipeline pipeline;

    @GetMapping("")
    public List<ActorDto> getAllActors() {
        return pipeline.send(new GetActorsQuery());
    }

    @GetMapping("{id}")
    public ResponseEntity<Actor> getActor(@PathVariable String id) {
        val result = pipeline.send(new GetActorDetailsQuery(id));
        return new ResponseEntity<>(
                result.orElse(null),
                result.isEmpty() ? HttpStatus.NOT_FOUND : HttpStatus.OK
        );
    }
}
