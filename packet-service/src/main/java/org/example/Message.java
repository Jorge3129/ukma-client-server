package org.example;

import java.util.Arrays;
import java.util.Objects;

public class Message {
    private CommandType commandType;
    private int userId;
    private byte[] body;

    public Message() {
        this.commandType = CommandType.CREATE_ARTICLE;
        this.userId = 1;
        this.body = new byte[]{1, 2, 3};
    }

    public Message(CommandType commandType, int bUserId, byte[] body) {
        this.commandType = commandType;
        this.userId = bUserId;
        this.body = body;
    }

    public CommandType getCommandType() {
        return commandType;
    }

    public int getUserId() {
        return userId;
    }

    public byte[] getBody() {
        return body;
    }

    @Override
    public String toString() {
        return "Message{" +
                "commandType=" + commandType +
                ", userId=" + userId +
                ", message=" + new String(body) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return userId == message.userId
                && commandType == message.commandType
                && Arrays.equals(body, message.body);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(commandType, userId);
        result = 31 * result + Arrays.hashCode(body);
        return result;
    }
}
