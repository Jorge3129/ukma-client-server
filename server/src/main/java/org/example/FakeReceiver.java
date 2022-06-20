package org.example;

import java.util.List;
import java.util.Random;

public class FakeReceiver implements IReceiver {

    private static final List<CommandType> VALUES = List.of(CommandType.values());
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();
    private static final IDecryptor decryptor = new Decryptor();
    private static final IPacketBuilder packetBuilder = new PacketBuilder();

    @Override
    public void receiveMessage() {
        Packet packet = generatePacket();
        byte[] encodedPacket = packetBuilder.encode(packet);
        decryptor.decrypt(encodedPacket);
    }

    @Override
    public void receiveMessage(long packetId) {
        Packet packet = generatePacket(packetId);
        byte[] encodedPacket = packetBuilder.encode(packet);
        decryptor.decrypt(encodedPacket);
    }

    private Packet generatePacket() {
        return new Packet((byte) RANDOM.nextInt(), RANDOM.nextLong(), generateMessage());
    }

    private Packet generatePacket(long packetId) {
        return new Packet((byte) RANDOM.nextInt(), packetId, generateMessage());
    }

    private Message generateMessage() {
        CommandType type = FakeReceiver.generateRandomCommand();
        int userId = RANDOM.nextInt();
        return new Message(type, userId, new byte[]{1, 2, 3});
    }

    private static CommandType generateRandomCommand() {
//        return VALUES.get(RANDOM.nextInt(SIZE));
        return CommandType.CREATE_ARTICLE;
    }

    public static void main(String[] args) {
        IReceiver receiver = new FakeReceiver();
        for (int i = 0; i < 10; i++) {
            receiver.receiveMessage(i);
        }
    }
}
