package com.example.processors;

import com.example.events.Event;

public interface EventProcessor {
    void processEvent(Event event);
}

