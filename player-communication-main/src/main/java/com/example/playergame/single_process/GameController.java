package com.example.playergame.single_process;

import com.example.playergame.common.Message;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class GameController {

    public void startGame() {
        BlockingQueue<Message> queue1 = new ArrayBlockingQueue<>(20);
        BlockingQueue<Message> queue2 = new ArrayBlockingQueue<>(20);

        Player player1 = new Player("Player1", queue1, true);
        Player player2 = new Player("Player2", queue2, false);

        player1.setPeerInbox(queue2);
        player2.setPeerInbox(queue1);

        Thread thread1 = new Thread(player1);
        Thread thread2 = new Thread(player2);

        thread1.start();
        thread2.start();

    }
}
