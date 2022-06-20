package org.example;

public interface IEncryptor {
    void encrypt(Packet packet) throws InterruptedException;
}
