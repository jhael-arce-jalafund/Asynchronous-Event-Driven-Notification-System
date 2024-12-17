package com.example.notifications;

import java.util.concurrent.CompletableFuture;
import com.example.domain.User;


public class NotificationService implements INotificationService {
    @Override
    public void sendNotification(User user, String message) {
        CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(2000);
                System.out.println("Notification sent to " + user.getName() + ": " + message);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Notification interrupted for " + user.getName());
            }
        });
    }
}
