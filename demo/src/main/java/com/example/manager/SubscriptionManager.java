package com.example.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.example.events.Event;
import com.example.subscription.Subscription;


public class SubscriptionManager {
    private final List<Subscription> subscriptions = new ArrayList<>();

    public void addSubscription(Subscription subscription) {
        subscriptions.add(subscription);
        System.out.println("Added subscription: " + subscription.getUser().getName());
    }

    public void notifySubscribers(Event event) {
        for (Subscription subscription : subscriptions) {
            if (subscription.matchesEvent(event)) {
                CompletableFuture.runAsync(() -> {
                    System.out.println("Notifying " + subscription.getUser().getName() + " about " + event);
                });
            }
          }      
        }

    public List<Subscription> getSubscriptions() {
            return subscriptions;
    }
}
