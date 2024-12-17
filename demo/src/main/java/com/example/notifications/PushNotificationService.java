package com.example.notifications;

import java.util.concurrent.CompletableFuture;

import com.example.domain.User;

public class PushNotificationService implements INotificationService {
    @Override
    public void sendNotification(User user, String message) {
        CompletableFuture.runAsync(() -> {
            try {
                // Simula el envío de una notificación push
                System.out.println("Sending PUSH Notification to " + user.getName());
                Thread.sleep(1200); // Simula la demora
                System.out.println("PUSH Notification: " + message);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Failed to send PUSH notification to " + user.getName());
            }
        });
    }
}