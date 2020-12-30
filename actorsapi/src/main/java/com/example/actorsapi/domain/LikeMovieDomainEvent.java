package com.example.actorsapi.domain;

import com.example.shared.domain.DomainEvent;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
public class LikeMovieDomainEvent extends DomainEvent {

    int likes;
}