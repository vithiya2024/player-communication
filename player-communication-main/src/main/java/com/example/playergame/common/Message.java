package com.example.playergame.common;

/**
 * Immutable value object for messages exchanged between players.
 */
import java.io.Serializable;

/**
 * Immutable value object for messages exchanged between players.
 */
public class Message implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String content;
    private final int counter;

    public Message(String content, int counter) {
        this.content = content;
        this.counter = counter;
    }

    public String getContent() {
        return content;
    }

    public int getCounter() {
        return counter;
    }

    @Override
    public String toString() {
        return content + " [#" + counter + "]";
    }
}
