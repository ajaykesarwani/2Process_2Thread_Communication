package com.example.sameprocess;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Manages communication between two Player instances using blocking queues.
 * Provides the message processing logic and coordinates the player threads.
 */


public class MessageExchange {
    private final BlockingQueue<String> player1ToPlayer2 = new LinkedBlockingQueue<>();
    private final BlockingQueue<String> player2ToPlayer1 = new LinkedBlockingQueue<>();
    private final Player player1;
    private final Player player2;

    public MessageExchange() {
        this.player1 = new Player("Player1", player2ToPlayer1, player1ToPlayer2, true);
        this.player2 = new Player("Player2", player1ToPlayer2, player2ToPlayer1, false);
    }

    public void startExchange() throws InterruptedException {
        Thread t1 = new Thread(player1);
        Thread t2 = new Thread(player2);

        t1.start();
        t2.start();

        t1.join();
        t2.join();
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }
    
    public static String processMessage(String message) {
        if (message.equalsIgnoreCase("exit")) {
            return "Goodbye!";
        }
        return "Echo: " + message;
    }
}