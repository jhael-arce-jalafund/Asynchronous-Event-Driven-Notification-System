package com.example.broker;

import com.example.events.Event;
import com.example.manager.SubscriptionManager;

public class EventPublisher {
    private final SubscriptionManager subscriptionManager;

    public EventPublisher(SubscriptionManager subscriptionManager) {
        this.subscriptionManager = subscriptionManager;
    }

    public void publishEvent(Event event) {
        System.out.println("Publishing event: " + event);
        subscriptionManager.notifySubscribers(event);
    }
}

