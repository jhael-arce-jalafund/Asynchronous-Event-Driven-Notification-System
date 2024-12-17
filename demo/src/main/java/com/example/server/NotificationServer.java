package com.example.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import com.example.broker.EventPublisher;
import com.example.events.Event;

public class NotificationServer {
    private final int port;
    private final EventPublisher eventPublisher;

    public NotificationServer(int port, EventPublisher eventPublisher) {
        this.port = port;
        this.eventPublisher = eventPublisher;
    }

    public void start() {
        System.out.println("Starting Notification Server on port: " + port);
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket.getInetAddress());

                new Thread(() -> handleClient(clientSocket)).start();
            }
        } catch (IOException e) {
        }
    }

    private void handleClient(Socket clientSocket) {
        try (ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
             ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream())) {

            Object obj = in.readObject();
            if (obj instanceof Event event) {
                System.out.println("Received event: " + event);
                eventPublisher.publishEvent(event);
            }

            out.writeObject("Event processed successfully");
        } catch (IOException | ClassNotFoundException e) {
        }
    }
}
