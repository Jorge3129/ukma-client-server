package org.example;

public class Encryptor implements IEncryptor {

    private static final IPacketBuilder packetBuilder = new PacketBuilder();
    private static final ISender sender = new FakeSender();

    @Override
    public void encrypt(Packet packet) throws InterruptedException {
        Thread t = new Thread(() -> {
            byte[] encodedPacket = packetBuilder.encode(packet);
            sender.sendMessage(encodedPacket, new NetAddress("http://localhost:3000"));
        });
        t.start();
        t.join();
    }
}
