package com.example.notifications;

import com.example.domain.User;


public interface INotificationService {
    void sendNotification(User user, String message);
}
