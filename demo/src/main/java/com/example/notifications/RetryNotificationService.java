package com.example.notifications;

import java.util.concurrent.CompletableFuture;

import com.example.domain.User;

public class RetryNotificationService extends NotificationService {
    private static final int MAX_RETRIES = 3;

    @Override
    public void sendNotification(User user, String message) {
        CompletableFuture.runAsync(() -> retrySend(user, message, 0));
    }

    private void retrySend(User user, String message, int attempt) {
        try {
            if (attempt > 0) {
                System.out.println("Retrying notification (Attempt " + attempt + ")...");
            }
            Thread.sleep(2000); 
            System.out.println("Notification sent to " + user.getName() + ": " + message);
        } catch (InterruptedException e) {
            if (attempt < MAX_RETRIES) {
                retrySend(user, message, attempt + 1);
            } else {
                System.err.println("Failed to send notification to " + user.getName());
            }
        }
    }
}
