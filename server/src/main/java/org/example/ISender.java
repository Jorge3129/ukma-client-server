package org.example;

public interface ISender {
    void sendMessage(byte[] message, INetAddress address);
}
