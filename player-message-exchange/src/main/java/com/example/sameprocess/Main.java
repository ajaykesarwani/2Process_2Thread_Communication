package com.example.sameprocess;

/**
 * Entry point for the message exchange program.
 * Creates and starts the message exchange between two players,
 * then prints the final message counts.
 */

public class Main {
    public static void main(String[] args) {
        try {
            MessageExchange exchange = new MessageExchange();
            exchange.startExchange();
            System.out.println("Program completed gracefully.");
            
            // Print final counts
            System.out.println("\nFinal Counts:");
            System.out.println("Player1 sent: " + exchange.getPlayer1().getSentMessageCount() + 
                             ", received: " + exchange.getPlayer1().getReceivedMessageCount());
            System.out.println("Player2 sent: " + exchange.getPlayer2().getSentMessageCount() + 
                             ", received: " + exchange.getPlayer2().getReceivedMessageCount());
        } catch (InterruptedException e) {
            System.out.println("Main thread was interrupted.");
            Thread.currentThread().interrupt();
        }
    }
}