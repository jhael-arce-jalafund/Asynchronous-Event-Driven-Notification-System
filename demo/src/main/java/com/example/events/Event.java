package com.example.events;

import java.time.LocalDateTime;

public abstract class Event {
    private final LocalDateTime timestamp;

    public Event() {
        this.timestamp = LocalDateTime.now();
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "Event at " + timestamp;
    }
}
