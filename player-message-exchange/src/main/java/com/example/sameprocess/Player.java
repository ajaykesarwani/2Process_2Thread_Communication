package com.example.sameprocess;

import java.util.concurrent.BlockingQueue;

/**
 * Represents a participant in the message exchange.
 * Handles sending and receiving messages according to the protocol:
 * - Initiator sends first message
 * - Responds to received messages with counter
 * - Tracks sent/received message counts
 * - Stops after completing message quota
 */

public class Player implements Runnable {
    private final String name;
    private final BlockingQueue<String> inbox;
    private final BlockingQueue<String> outbox;
    private volatile int sentMessageCount = 0;
    private volatile int receivedMessageCount = 0;
    private final boolean isInitiator;
    private final int maxMessages = 10;

    public Player(String name, BlockingQueue<String> inbox, BlockingQueue<String> outbox, boolean isInitiator) {
        this.name = name;
        this.inbox = inbox;
        this.outbox = outbox;
        this.isInitiator = isInitiator;
    }

    @Override
    public void run() {
        try {
            if (isInitiator) {
                // Initiator sends just 1 initial message
                sendInitialMessage();
            }

            while (shouldContinue()) {
                String receivedMessage = inbox.take();
                receivedMessageCount++;
                System.out.println(name + " received: " + receivedMessage);

                if (shouldRespond()) {
                    String response = createResponse(receivedMessage);
                    outbox.put(response);
                    sentMessageCount++;
                    System.out.println(name + " sent: " + response);
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println(name + " was interrupted.");
        }
    }

    private void sendInitialMessage() throws InterruptedException {
        String message = "Hello from " + name;
        outbox.put(message);
        sentMessageCount++;
        System.out.println(name + " sent: " + message);
    }

    private String createResponse(String receivedMessage) {
        return receivedMessage + " - response #" + (sentMessageCount + 1);
    }

    private boolean shouldContinue() {
        if (isInitiator) {
            return receivedMessageCount < maxMessages;
        } else {
            return sentMessageCount < maxMessages;
        }
    }

    private boolean shouldRespond() {
        if (isInitiator) {
            return receivedMessageCount < maxMessages;
        } else {
            return true;
        }
    }

    public int getSentMessageCount() {
        return sentMessageCount;
    }

    public int getReceivedMessageCount() {
        return receivedMessageCount;
    }
}