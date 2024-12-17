package com.example.notifications;

import java.util.concurrent.CompletableFuture;

import com.example.domain.User;

public class SMSNotificationService implements INotificationService {
    @Override
    public void sendNotification(User user, String message) {
        CompletableFuture.runAsync(() -> {
            try {
                // Simula el envío de un SMS
                System.out.println("Sending SMS to " + user.getName() + "...");
                Thread.sleep(1000); // Simula la demora
                System.out.println("SMS Notification: " + message);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Failed to send SMS to " + user.getName());
            }
        });
    }
}
