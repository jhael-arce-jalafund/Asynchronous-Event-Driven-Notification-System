package com.example.handlers;

import java.time.LocalDate;
import java.util.Scanner;

import com.example.events.Event;
import com.example.events.TaskAssignedEvent;
import com.example.events.TaskCompletedEvent;
import com.example.events.TaskDeadlineEvent;

public class UserInputHandler {

    public Event handleUserInput(Scanner scanner) { 
        System.out.println("Choose event type: 1. TaskAssigned 2. TaskCompleted 3. TaskDeadline");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1 -> {
                System.out.println("Enter task ID:");
                String taskId = scanner.next();
                System.out.println("Enter user assigned to:");
                String assignedTo = scanner.next();
                System.out.println("Enter task description:");
                scanner.nextLine(); // Consume leftover newline
                String description = scanner.nextLine();
                return new TaskAssignedEvent(taskId, assignedTo, description);
            }
            case 2 -> {
                System.out.println("Enter task ID:");
                String taskId = scanner.next();
                System.out.println("Enter user who completed the task:");
                String completedBy = scanner.next();
                return new TaskCompletedEvent(taskId, completedBy);
            }
            case 3 -> {
                System.out.println("Enter task ID:");
                String taskId = scanner.next();
                System.out.println("Enter task deadline (yyyy-MM-dd):");
                String deadline = scanner.next();
                return new TaskDeadlineEvent(taskId, LocalDate.parse(deadline));
            }
            default -> {
                System.out.println("Invalid choice");
                return null;
            }
        }
    }
}
