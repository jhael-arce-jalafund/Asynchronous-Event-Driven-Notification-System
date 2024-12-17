package com.example.subscription;

import com.example.domain.User;

public class TaskSubscription extends Subscription {
    public TaskSubscription(User user) {
        super(user, "TaskEvents");
    }
}
