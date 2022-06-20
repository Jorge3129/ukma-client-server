package org.example;

public interface IReceiver {
    void receiveMessage();
    void receiveMessage(long packetId);
}
