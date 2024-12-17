package com.example;

import java.util.List;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import com.example.Client.ClientSocket;
import com.example.broker.EventPublisher;
import com.example.domain.User;
import com.example.events.Event;
import com.example.events.TaskAssignedEvent;
import com.example.events.TaskCompletedEvent;
import com.example.events.TaskDeadlineEvent;
import com.example.handlers.UserInputHandler;
import com.example.manager.SubscriptionManager;
import com.example.notifications.INotificationService;
import com.example.notifications.NotificationService;
import com.example.notifications.PushNotificationService;
import com.example.notifications.RetryNotificationService;
import com.example.notifications.SMSNotificationService;
import com.example.server.NotificationServer;
import com.example.subscription.Subscription;

public class App {
    private static final BlockingQueue<Event> eventQueue = new LinkedBlockingQueue<>();
    private static final SubscriptionManager subscriptionManager = new SubscriptionManager();
    private static final EventPublisher eventPublisher = new EventPublisher(subscriptionManager);

    private static final INotificationService emailService = new NotificationService();
    private static final INotificationService smsService = new SMSNotificationService();
    private static final INotificationService pushService = new PushNotificationService();
    private static final INotificationService retryService = new RetryNotificationService();

    public static void main(String[] args) {
        // Inicializar las suscripciones
        initializeSubscriptions();
        
        // Iniciar el servidor para procesar los eventos
        startNotificationServer();

        // Iniciar el Event Listener en un hilo separado para consumir eventos de la cola
        startEventListener();

        // Simular la entrada de eventos desde un cliente
        simulateEventInput();
    }

    private static void initializeSubscriptions() {
        User user1 = new User("1", "Alice", "alice@example.com");
        User user2 = new User("2", "Bob", "bob@example.com");

        subscriptionManager.addSubscription(new Subscription(user1, "TaskAssignedEvent"));
        subscriptionManager.addSubscription(new Subscription(user2, "TaskCompletedEvent"));
        subscriptionManager.addSubscription(new Subscription(user2, "TaskDeadlineEvent"));
    }

    private static void startNotificationServer() {
        // Iniciar el servidor de notificación en un hilo separado para escuchar los eventos de los clientes
        NotificationServer notificationServer = new NotificationServer(8080, eventPublisher);
        new Thread(notificationServer::start).start();
    }

    private static void startEventListener() {
        ExecutorService eventProcessorExecutor = Executors.newFixedThreadPool(4);

        // Consumidor de eventos
        Runnable eventListener = () -> {
            while (true) {
                try {
                    Event event = eventQueue.take(); // Bloquea si la cola está vacía
                    System.out.println("Processing event: " + event);

                    // Procesamiento asíncrono del evento
                    CompletableFuture.runAsync(() -> {
                        eventPublisher.publishEvent(event);
                        notifyUsers(event);
                    }, eventProcessorExecutor);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.err.println("Event listener interrupted");
                }
            }
        };

        new Thread(eventListener, "EventListenerThread").start();
    }

    private static void simulateEventInput() {
        Scanner scanner = new Scanner(System.in);
        UserInputHandler userInputHandler = new UserInputHandler();

        // Crear un cliente para enviar eventos al servidor
        ClientSocket clientSocket = new ClientSocket("localhost", 8080);

        while (true) {
            Event event = userInputHandler.handleUserInput(scanner);

            if (event != null) {
                System.out.println("Event added to queue: " + event);

                // Enviar el evento al servidor a través del cliente
                clientSocket.sendEvent(event);
                eventPublisher.publishEvent(event);
            }
        }
    }

    private static void notifyUsers(Event event) {
        List<Subscription> subscriptions = subscriptionManager.getSubscriptions();

        for (Subscription subscription : subscriptions) {
            if (subscription.matchesEvent(event)) {
                User user = subscription.getUser();
                String message = generateNotificationMessage(event);

                // Enviar notificaciones asíncronamente
                CompletableFuture.runAsync(() -> {
                    emailService.sendNotification(user, message);
                    smsService.sendNotification(user, message);
                    pushService.sendNotification(user, message);
                    retryService.sendNotification(user, message);
                });
            }
        }
    }

    private static String generateNotificationMessage(Event event) {
        switch (event) {
            case TaskAssignedEvent assignedEvent -> {
                return "Task Assigned: " + assignedEvent.getDescription();
            }
            case TaskCompletedEvent completedEvent -> {
                return "Task Completed: Task ID " + completedEvent.getTaskId();
            }
            case TaskDeadlineEvent deadlineEvent -> {
                return "Task Deadline: Task ID " + deadlineEvent.getTaskId() + " due on " + deadlineEvent.getDeadline();
            }
            default -> {
            }
        }
        return "Unknown event";
    }
}
