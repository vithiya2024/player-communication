package com.example.playergame.common;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MessageTest {

    @Test
    void testConstructorAndGetters() {
        // Arrange
        String content = "Hello";
        int counter = 1;

        // Act
        Message message = new Message(content, counter);

        // Assert
        assertEquals(content, message.getContent());
        assertEquals(counter, message.getCounter());
    }

    @Test
    void testToString() {
        // Arrange
        String content = "Hello";
        int counter = 1;
        Message message = new Message(content, counter);

        // Act
        String result = message.toString();

        // Assert
        assertEquals("Hello [#1]", result);
    }
}