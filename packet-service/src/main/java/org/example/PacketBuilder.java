package org.example;

public class PacketBuilder implements IPacketBuilder {

    private static final String CIPHER_ALGORITHM = "AES/CBC/NoPadding";
    private final MyCipher cipher;

    public PacketBuilder() {
        try {
            this.cipher = new MyCipher(CIPHER_ALGORITHM);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] encode(Packet packet) throws RuntimeException {
        try {
            byte[] message = MyMapper.writeMessageAsBytes(packet.getMessage());
            byte[] header = PacketByteConverter.createHeader(packet, message);
//            System.out.println(Arrays.toString(message));
            short headerCRC = CRC16.calculate(header);
            short messageCRC = CRC16.calculate(message);
//            byte[] encryptedMessage = this.cipher.encrypt(message);
            byte[] encryptedMessage = message;
            return PacketByteConverter.createPacket(header, encryptedMessage, headerCRC, messageCRC);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Packet decode(byte[] data) throws RuntimeException {
        try {
            PacketDTO packetDTO = PacketByteConverter.extractData(data);
            byte[] messageBuffer = PacketByteConverter.createMessageBuffer(data, packetDTO.getMessageLength());
//            byte[] decryptedMessageBuffer = this.cipher.decrypt(messageBuffer);
            byte[] decryptedMessageBuffer = messageBuffer;
            validateData(packetDTO, decryptedMessageBuffer);
            Message message = MyMapper.readMessage(decryptedMessageBuffer);
            return new Packet(packetDTO.getClientId(), packetDTO.getPacketId(), message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void validateData(PacketDTO packetDTO, byte[] messageData) {
        if (!headerIsValid(packetDTO)) throw new RuntimeException("Header is not valid");
        if (!messageIsValid(packetDTO, messageData)) throw new RuntimeException("Message is not valid");
    }

    private boolean headerIsValid(PacketDTO packetDTO) {
        return packetDTO.getHeaderCRC() == CRC16.calculate(packetDTO.getHeader());
    }

    private boolean messageIsValid(PacketDTO packetDTO, byte[] messageBuffer) {
        return packetDTO.getMessageCRC() == CRC16.calculate(messageBuffer);
    }


    public static void main(String[] args) {
        try {
//             create instance of PacketBuilder
            IPacketBuilder packetBuilder = new PacketBuilder();
            // create Message and packet
            Message message = new Message(CommandType.CREATE_ARTICLE, 1, new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12});
            Packet packet = new Packet((byte) 1, 1, message);

            // PacketBuilder.encode
            byte[] encodedPacket = packetBuilder.encode(packet);
            System.out.println(new String(encodedPacket));

            // PacketBuilder.decode
            Packet decodedPacket = packetBuilder.decode(encodedPacket);
            System.out.println(decodedPacket.getMessage());
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }
}
