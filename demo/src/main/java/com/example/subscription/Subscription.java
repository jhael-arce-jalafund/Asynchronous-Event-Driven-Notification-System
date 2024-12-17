package com.example.subscription;

import com.example.domain.User;
import com.example.events.Event;

public class Subscription {
    private final User user;
    private final String eventType;

    public Subscription(User user, String eventType) {
        this.user = user;
        this.eventType = eventType;
    }

    public User getUser() {
        return user;
    }

    public String getEventType() {
        return eventType;
    }

    public boolean matchesEvent(Event event) {
        return event.getClass().getSimpleName().equalsIgnoreCase(eventType);
    }
}
