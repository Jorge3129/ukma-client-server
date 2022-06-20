package org.example;

public interface IPacketBuilder {
    byte[] encode(Packet packet) throws RuntimeException;
    Packet decode(byte[] data) throws RuntimeException;
}
