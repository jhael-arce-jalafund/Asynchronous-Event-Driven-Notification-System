package com.example.events;

import java.io.Serializable;

public class TaskCompletedEvent extends Event implements  Serializable {
    private static final long serialVersionUID = 1L;
    private final String taskId;
    private final String completedBy;

    public TaskCompletedEvent(String taskId, String completedBy) {
        this.taskId = taskId;
        this.completedBy = completedBy;
    }

    public String getTaskId() {
        return taskId;
    }

    public String getCompletedBy() {
        return completedBy;
    }

    @Override
    public String toString() {
        return "TaskCompletedEvent{" +
                "taskId='" + taskId + '\'' +
                ", completedBy='" + completedBy + '\'' +
                '}';
    }
}

