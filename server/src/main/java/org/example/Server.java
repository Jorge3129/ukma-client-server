package org.example;

public class Server {
    public static void main(String[] args) {
        new FakeReceiver().receiveMessage();
        new Decryptor().decrypt();
        new FakeProcessor().process();
        new Encryptor().encrypt();
    }
}
