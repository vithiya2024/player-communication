package com.example.playergame.multi_process;

import com.example.playergame.common.Message;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Player running as client (initiator). Sends first message and exchanges 10 round-trips.
 */
public class PlayerClient {
    private final String host;
    private final int port;
    private final int maxMessages = 10;

    public PlayerClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void start() {
        try (Socket socket = new Socket(host, port);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            int counter = 0;
            // Send initial message
            counter++;
            Message msg = new Message("Hello", counter);
            out.writeObject(msg);
            out.flush();
            System.out.println("[Client] Sent: " + msg);

            int received = 0;
            while (received < maxMessages) {
                Message reply = (Message) in.readObject();
                if (reply == null) break;
                System.out.println("[Client] Received: " + reply);
                received++;
                if (received >= maxMessages) break;

                counter++;
                Message next = new Message(reply.getContent() + " -> reply", counter);
                out.writeObject(next);
                out.flush();
                System.out.println("[Client] Sent: " + next);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("[Client] Shutting down..");
    }

    public static void main(String[] args) {
        String host = "localhost";
        int port = 5000;
        if (args.length > 0) host = args[0];
        if (args.length > 1) port = Integer.parseInt(args[1]);
        new PlayerClient(host, port).start();
    }
}
