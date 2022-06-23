package org.example;

import org.example.interfaces.IDecryptor;

import java.util.concurrent.*;

public class Decryptor implements IDecryptor {

    public static final BlockingQueue<Packet> packetQueue = new ArrayBlockingQueue<>(10);

    @Override
    public void decrypt() {
        new Thread(() -> {
            try {
                byte[] data = FakeReceiver.byteQueue.poll(10L, TimeUnit.SECONDS);
                Packet result = new PacketBuilder().decode(data);
                packetQueue.put(result);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }).start();
    }
}
