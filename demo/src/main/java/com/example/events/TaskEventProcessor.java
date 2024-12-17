package com.example.events;

import java.util.concurrent.CompletableFuture;

import com.example.domain.User;
import com.example.notifications.INotificationService;
import com.example.processors.EventProcessor;

public class TaskEventProcessor implements EventProcessor {
    private final INotificationService notificationService;

    public TaskEventProcessor(INotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @Override
    public void processEvent(Event event) {
        CompletableFuture.runAsync(() -> {
            switch (event) {
                case TaskAssignedEvent taskAssigned -> sendNotification(taskAssigned.getAssignedTo(), "Task assigned: " + taskAssigned.getDescription());
                case TaskCompletedEvent taskCompleted -> sendNotification(taskCompleted.getCompletedBy(), "Task completed: " + taskCompleted.getTaskId());
                case TaskDeadlineEvent taskDeadline -> sendNotification("admin", "Task deadline: " + taskDeadline.getTaskId() + " by " + taskDeadline.getDeadline());
                default -> {
                }
            }
        });
    }

    private void sendNotification(String userName, String message) {
        User user = new User(userName, userName, userName + "@example.com");
        notificationService.sendNotification(user, message);
    }
}
