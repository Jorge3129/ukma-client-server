package org.example;

import org.example.interfaces.IEncryptor;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class Encryptor implements IEncryptor {
    public static final BlockingQueue<byte[]> responseByteQueue = new ArrayBlockingQueue<>(10);


    @Override
    public void encrypt(Packet packet) {
        try {
            Thread t = new Thread(() -> {
                byte[] encodedPacket = new PacketBuilder().encode(packet);
                new FakeSender().sendMessage(encodedPacket, new NetAddress("http://localhost:3000"));
            });
            t.start();
            t.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void encrypt() {
        new Thread(() -> {
            try {
                Packet packet = FakeProcessor.responsePacketQueue.poll(10L, TimeUnit.SECONDS);
                byte[] encodedPacket = new PacketBuilder().encode(packet);
                responseByteQueue.put(encodedPacket);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }
}
