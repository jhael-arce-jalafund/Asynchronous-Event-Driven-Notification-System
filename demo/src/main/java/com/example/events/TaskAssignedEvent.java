package com.example.events;

import java.io.Serializable;

public class TaskAssignedEvent extends Event implements  Serializable {
    private static final long serialVersionUID = 1L;

    private final String taskId;
    private final String assignedTo;
    private final String description;

    public TaskAssignedEvent(String taskId, String assignedTo, String description) {
        this.taskId = taskId;
        this.assignedTo = assignedTo;
        this.description = description;
    }

    public String getTaskId() {
        return taskId;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "TaskAssignedEvent{" +
                "taskId='" + taskId + '\'' +
                ", assignedTo='" + assignedTo + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
