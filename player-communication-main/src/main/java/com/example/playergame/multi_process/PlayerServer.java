package com.example.playergame.multi_process;

import com.example.playergame.common.Message;

import java.io.EOFException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Player running as server. Waits for connection and replies to messages.
 */
public class PlayerServer {

    private final int port;

    public PlayerServer(int port) {
        this.port = port;
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("[Server] Waiting for client on port " + port + "...");
            try (Socket socket = serverSocket.accept();
                 ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                 ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

                int counter = 0;
                while (true) {
                    Message msg = (Message) in.readObject();
                    if (msg == null) break;
                    System.out.println("[Server] Received: " + msg);
                    counter++;
                    Message reply = new Message(msg.getContent() + " -> reply", counter);
                    out.writeObject(reply);
                    out.flush();
                }
            }
        } catch (EOFException e) {
            System.out.println("End of the communication!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("[Server] Shutting down..");
    }

    public static void main(String[] args) {
        int port = 5000;
        if (args.length > 0) port = Integer.parseInt(args[0]);
        new PlayerServer(port).start();
    }
}
