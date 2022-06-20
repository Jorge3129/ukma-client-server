package org.example;

import java.util.Objects;

public class Packet {
    private byte clientId;
    private long packetId;
    private Message message;

    public Packet(byte clientId, long packetId, Message message) {
        this.clientId = clientId;
        this.packetId = packetId;
        this.message = message;
    }

    public byte getClientId() {
        return clientId;
    }

    public long getPacketId() {
        return packetId;
    }

    public Message getMessage() {
        return message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Packet packet = (Packet) o;
        return clientId == packet.clientId
                && packetId == packet.packetId
                && message.equals(packet.message);
    }

    @Override
    public String toString() {
        return "Packet { " +
                "packetId = " + packetId +
                ", clientId = " + clientId +
                ", message = " + message +
                " }";
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientId, packetId, message);
    }
}
