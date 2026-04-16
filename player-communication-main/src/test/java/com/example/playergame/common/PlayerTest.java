package com.example.playergame.common;

import com.example.playergame.single_process.Player;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


class PlayerTest {

    @Test
    void shouldSendMessageToPeerInbox() throws InterruptedException {
        BlockingQueue<Message> inbox1 = new ArrayBlockingQueue<>(10);
        BlockingQueue<Message> inbox2 = new ArrayBlockingQueue<>(10);

        Player p1 = new Player("P1", inbox1, true);
        p1.setPeerInbox(inbox2);

        p1.sendMessage("Hello");

        Message received = inbox2.take();

        assertEquals("Hello", received.getContent());
        assertEquals(1, received.getCounter());
    }

    @Test
    void shouldIncrementCounterOnEachSend() throws InterruptedException {
        BlockingQueue<Message> inbox1 = new ArrayBlockingQueue<>(10);
        BlockingQueue<Message> inbox2 = new ArrayBlockingQueue<>(10);

        Player p1 = new Player("P1", inbox1, true);
        p1.setPeerInbox(inbox2);

        p1.sendMessage("Hi");
        p1.sendMessage("Hi again");

        Message first = inbox2.take();
        Message second = inbox2.take();

        assertEquals(1, first.getCounter());
        assertEquals(2, second.getCounter());
    }

    @Test
    void shouldAppendReplyToMessage() throws InterruptedException {
        BlockingQueue<Message> inbox1 = new ArrayBlockingQueue<>(10);
        BlockingQueue<Message> inbox2 = new ArrayBlockingQueue<>(10);

        Player p1 = new Player("P1", inbox1, false);
        p1.setPeerInbox(inbox2);

        Message msg = new Message("Hello", 1);
        inbox1.put(msg);

        Thread t = new Thread(p1);
        t.start();
        Thread.sleep(500);

        Message reply = inbox2.take();
        assertTrue(reply.getContent().contains("reply"));
        t.interrupt();
    }

}
