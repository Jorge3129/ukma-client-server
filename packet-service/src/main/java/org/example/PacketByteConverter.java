package org.example;

import java.nio.ByteBuffer;

public class PacketByteConverter {
    // constants for sizes of specific fragments of packet
    private static final byte B_MAGIC = 0x13;
    private static final short B_MAGIC_SIZE = 1;
    private static final short CLIENT_ID_SIZE = 1;
    private static final short PACKET_ID_SIZE = 8;
    private static final short PACKET_LENGTH_SIZE = 4;
    private static final short CRC_SIZE = 2;
    private static final short HEADER_SIZE = B_MAGIC_SIZE + CLIENT_ID_SIZE + PACKET_ID_SIZE + PACKET_LENGTH_SIZE;

    public static byte[] createHeader(Packet packet, byte[] message) {
        return ByteBuffer.allocate(HEADER_SIZE)
                .put(B_MAGIC)
                .put(packet.getClientId())
                .putLong(packet.getPacketId())
                .putInt(message.length)
                .array();
    }

    public static byte[] createPacket(byte[] header, byte[] message, short headerCRC, short messageCRC) {
        return ByteBuffer.allocate(HEADER_SIZE + message.length + 2 * CRC_SIZE)
                .put(header)
                .putShort(headerCRC)
                .put(message)
                .putShort(messageCRC)
                .array();
    }

    public static PacketDTO extractData(byte[] data) {
        ByteBuffer wrapper = ByteBuffer.wrap(data);
        // client and packet ids, message length
        byte clientId = wrapper.get(B_MAGIC_SIZE);
        long packetId = wrapper.getLong(B_MAGIC_SIZE + CLIENT_ID_SIZE);
        int messageLength = wrapper.getInt(B_MAGIC_SIZE + CLIENT_ID_SIZE + PACKET_ID_SIZE);
        // entire header for CRC check
        byte[] header = new byte[HEADER_SIZE];
        wrapper.get(header, 0, HEADER_SIZE);
        // The two CRC sums
        short headerCRC = wrapper.getShort(HEADER_SIZE);
        short messageCRC = wrapper.getShort(HEADER_SIZE + CRC_SIZE + messageLength);

        return new PacketDTO(
                header,
                clientId,
                packetId,
                messageLength,
                headerCRC,
                messageCRC
        );
    }

    public static byte[] createMessageBuffer(byte[] data, int messageLength) {
        byte[] messageBuffer = new byte[messageLength];
        System.arraycopy(data, HEADER_SIZE + CRC_SIZE, messageBuffer, 0, messageLength);
        return messageBuffer;
    }
}
