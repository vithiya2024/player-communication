package com.example.playergame.single_process;

import com.example.playergame.common.Message;

import java.util.concurrent.BlockingQueue;

/**
 * Represents a player that can send and receive messages.
 * Each player runs on its own thread within the same JVM.
 */
public class Player implements Runnable {

    private final String name;
    private final BlockingQueue<Message> inbox;
    private final boolean initiator;
    private final int maxMessages = 10;
    private BlockingQueue<Message> peerInbox;
    private int counter = 0;
    private int messagesExchanged = 0;
    private volatile boolean running = true;


    public Player(String name, BlockingQueue<Message> inbox, boolean initiator) {
        this.name = name;
        this.inbox = inbox;
        this.initiator = initiator;
    }

    public void setPeerInbox(BlockingQueue<Message> peerInbox) {
        this.peerInbox = peerInbox;
    }

    public void sendMessage(String content) throws InterruptedException {
        counter++;
        Message message = new Message(content, counter);
        peerInbox.put(message);
        System.out.println(name + " sent: " + message);
    }

    private void handleMessage(Message message) throws InterruptedException {
        System.out.println(name + " received: " + message);
        messagesExchanged++;
        if (initiator && messagesExchanged >= maxMessages) {
            running = false;
            System.exit(0);
        }
        sendMessage(message.getContent() + " -> reply");
    }

    @Override
    public void run() {
        try {
            if (initiator) {
                sendMessage("Hello");
            }
            while (running) {
                Message msg = inbox.take();
                handleMessage(msg);
            }
            System.out.println(name + " shutting down..");
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
    }
}
