package org.example;

public class FakeSender implements ISender {
    private static final IPacketBuilder packetBuilder = new PacketBuilder();
    @Override
    public void sendMessage(byte[] message, INetAddress address) {
        new Thread(() -> {
            //System.out.println(new String(message));
        }).start();
    }
}
