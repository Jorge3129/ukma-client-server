package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class MyMapper {
    // ObjectMapper instance
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static byte[] writeMessageAsBytes(Message value) throws JsonProcessingException {
        return objectMapper.writeValueAsBytes(value);
    }

    public static Message readMessage(byte[] message) throws IOException {
        return objectMapper.readValue(message, Message.class);
    }
}
