package com.example.Client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.example.events.Event;

public class ClientSocket {
    private final String host;
    private final int port;

    public ClientSocket(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void sendEvent(Event event) {
        try (Socket socket = new Socket(host, port);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            out.writeObject(event);
            out.flush();

            String response = (String) in.readObject();
            System.out.println("Server response: " + response);

        } catch (IOException | ClassNotFoundException e) {
        }
    }
}
