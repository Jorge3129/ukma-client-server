package org.example;

import java.util.Arrays;

class PacketDTO {
    private final byte clientId;
    private final long packetId;
    private final int messageLength;
    private final byte[] header;
    private final short headerCRC;
    private final short messageCRC;

    public byte[] getHeader() {
        return header;
    }

    public short getHeaderCRC() {
        return headerCRC;
    }

    public short getMessageCRC() {
        return messageCRC;
    }

    public PacketDTO(
            byte[] header,
            byte clientId,
            long packetId,
            int messageLength,
            short headerCRC,
            short messageCRC
    ) {
        this.header = header;
        this.clientId = clientId;
        this.packetId = packetId;
        this.messageLength = messageLength;
        this.headerCRC = headerCRC;
        this.messageCRC = messageCRC;
    }

    public byte getClientId() {
        return clientId;
    }

    public long getPacketId() {
        return packetId;
    }

    public int getMessageLength() {
        return messageLength;
    }

    @Override
    public String toString() {
        return "PacketDTO{" +
                "clientId=" + clientId +
                ", packetId=" + packetId +
                ", messageLength=" + messageLength +
                ", header=" + Arrays.toString(header) +
                ", headerCRC=" + headerCRC +
                ", messageCRC=" + messageCRC +
                '}';
    }
}
