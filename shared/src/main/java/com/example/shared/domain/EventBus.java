package com.example.shared.domain;

import lombok.NonNull;

import java.util.List;
import java.util.concurrent.Future;

public interface EventBus {

    void publishToQueue(@NonNull List<? extends DomainEvent> events);
}
