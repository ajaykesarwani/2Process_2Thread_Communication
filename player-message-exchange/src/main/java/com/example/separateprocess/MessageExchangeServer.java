package com.example.separateprocess;

import java.io.*;
import java.net.*;
import java.util.concurrent.locks.ReentrantLock;

/**
 * - Manages communication between player processes
 * Responsibilities:
 * - Listens for incoming player connections
 * - Maintains connections to both players
 * - Relays messages between connected players
 * - Provides thread-safe message passing
 */

public class MessageExchangeServer {
    private static final ReentrantLock outputLock = new ReentrantLock();

    public static void main(String[] args) throws IOException {
        System.out.println("Server started on port 5000");
        
        try (ServerSocket server = new ServerSocket(5000)) {
            // Wait for both players to connect
            PlayerConnection player1 = new PlayerConnection(server.accept());
            PlayerConnection player2 = new PlayerConnection(server.accept());

            // Start message relay between players
            new Thread(() -> relayMessages(player1, player2)).start();
            new Thread(() -> relayMessages(player2, player1)).start();
        }
    }

    private static void relayMessages(PlayerConnection from, PlayerConnection to) {
        try {
            String message;
            while ((message = from.in.readLine()) != null) {
                outputLock.lock();
                try {
                    System.out.printf("%s sent: %s\n", from.name, message);
                    to.out.println(message);
                    System.out.printf("%s received: %s\n", to.name, message);
                } finally {
                    outputLock.unlock();
                }
            }
        } catch (IOException e) {
            System.out.printf("Connection closed for %s\n", from.name);
        }
    }

    private static class PlayerConnection {
        final String name;
        final BufferedReader in;
        final PrintWriter out;

        PlayerConnection(Socket socket) throws IOException {
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.out = new PrintWriter(socket.getOutputStream(), true);
            this.name = in.readLine();
            System.out.println(name + " connected");
        }
    }
}