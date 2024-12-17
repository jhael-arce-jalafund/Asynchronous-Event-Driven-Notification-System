package com.example.events;
import java.io.Serializable;
import java.time.LocalDate;

public class TaskDeadlineEvent extends Event implements  Serializable {
    private static final long serialVersionUID = 1L;
    private final String taskId;
    private final LocalDate deadline;

    public TaskDeadlineEvent(String taskId, LocalDate deadline) {
        this.taskId = taskId;
        this.deadline = deadline;
    }

    public String getTaskId() {
        return taskId;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    @Override
    public String toString() {
        return "TaskDeadlineEvent{" +
                "taskId='" + taskId + '\'' +
                ", deadline=" + deadline +
                '}';
    }
}
