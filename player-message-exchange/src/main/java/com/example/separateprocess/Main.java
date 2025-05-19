package com.example.separateprocess;

import java.io.IOException;

/**
 * - Entry point that coordinates the startup of server and player processes
 * Responsibilities:
 * - Starts the message exchange server in a separate thread
 * - Launches two player processes with different parameters
 * - Waits for processes to complete
 * - Provides clean shutdown reporting
 */

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        // Start server
        new Thread(() -> {
            try {
                MessageExchangeServer.main(null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
        Thread.sleep(500); // Brief wait for server to start

        // Start players
        Process player1 = startPlayer("Player1", true);
        Process player2 = startPlayer("Player2", false);

        System.out.println("All processes launched.");

        // Wait for players to finish
        player1.waitFor();
        player2.waitFor();

        System.out.println("\nProgram completed successfully.");
    }

    private static Process startPlayer(String name, boolean isInitiator) throws IOException {
        return new ProcessBuilder(
            "java", "-cp", "target/classes", 
            "com.example.separateprocess.PlayerApp", 
            name, String.valueOf(isInitiator))
        .inheritIO().start();
    }
}