package org.example;

import org.example.interfaces.IReceiver;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class FakeReceiver implements IReceiver {
    private static final Random RANDOM = new Random();
    public static final BlockingQueue<byte[]> byteQueue = new ArrayBlockingQueue<>(10);

    @Override
    public void receiveMessage() {
        receiveMessage(RANDOM.nextLong());
    }

    @Override
    public void receiveMessage(long packetId) {
        new Thread(() -> {
            Packet packet = generatePacket(packetId);
            byte[] encodedPacket = new PacketBuilder().encode(packet);
            try {
                byteQueue.put(encodedPacket);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    private Packet generatePacket() {
        return new Packet((byte) RANDOM.nextInt(), RANDOM.nextLong(), generateMessage());
    }

    private Packet generatePacket(long packetId) {
        return new Packet((byte) RANDOM.nextInt(), packetId, generateMessage());
    }

    private Message generateMessage() {
        CommandType type = CommandType.CREATE_ARTICLE;
        int userId = RANDOM.nextInt();
        return new Message(type, userId, new byte[]{1, 2, 3});
    }

    public static void main(String[] args) {
        IReceiver receiver = new FakeReceiver();
        for (int i = 0; i < 10; i++) {
            receiver.receiveMessage(i);
        }
    }
}
