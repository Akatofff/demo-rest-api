package com.example.demo_rest_api;

import java.util.UUID;

public record Task(UUID id, String details, boolean isComplete) {

    public Task(String details) {
        this(UUID.randomUUID(), details, false);
    }
}
