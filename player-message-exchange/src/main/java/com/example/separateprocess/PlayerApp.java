package com.example.separateprocess;

import java.io.*;
import java.net.Socket;

/**
 * - Represents an individual player in a separate process
 * Responsibilities:
 * - Establishes connection to the message server
 * - Implements message sending/receiving logic
 * - Handles initiator/non-initiator behavior
 * - Enforces stop conditions
 */

public class PlayerApp {
    private static final int MAX_MESSAGES = 10;

    public static void main(String[] args) throws IOException {
    	//System.out.println("Player PID: " + ProcessHandle.current().pid());
        String name = args[0];
        boolean isInitiator = Boolean.parseBoolean(args[1]);

        try (Socket socket = new Socket("localhost", 5000);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            out.println(name); // Identify self to server

            int sent = 0, received = 0;

            if (isInitiator) {
                String msg = "Hello from " + name;
                out.println(msg);
                sent++;
            }

            while (shouldContinue(isInitiator, sent, received)) {
                String msg = in.readLine();
                if (msg == null) break;
                received++;

                if (shouldRespond(isInitiator, received)) {
                    String response = msg + " - response #" + (sent + 1); 
                    out.println(response);
                    sent++;
                }
            }

        }
    }

    private static boolean shouldContinue(boolean isInitiator, int sent, int received) {
        return isInitiator ? received < MAX_MESSAGES : sent < MAX_MESSAGES;
    }

    private static boolean shouldRespond(boolean isInitiator, int received) {
        return !isInitiator || received < MAX_MESSAGES;
    }
}