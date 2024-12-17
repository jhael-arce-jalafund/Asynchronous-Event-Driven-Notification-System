package com.example.notifications;

import java.util.concurrent.CompletableFuture;

import com.example.domain.User;

public class EmailNotificationService implements INotificationService {
    @Override
    public void sendNotification(User user, String message) {
        CompletableFuture.runAsync(() -> {
            try {
                System.out.println("Sending EMAIL to " + user.getEmail());
                Thread.sleep(1500); 
                System.out.println("EMAIL Notification: " + message);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Failed to send EMAIL notification to " + user.getName());
            }
        });
    }
}
