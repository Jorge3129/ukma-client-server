import org.example.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;


public class PacketBuilderTest {

    private IPacketBuilder packetBuilder;
    private Message message;
    private Packet packet;

    @BeforeEach
    public void init() {
        packetBuilder = new PacketBuilder();
        message = new Message(CommandType.CREATE_ARTICLE, 1, new byte[]{1, 2, 3});
        packet = new Packet((byte) 1, 1, message);
    }

    @Test
    public void shouldEncodeAndDecodePacket() {
        byte[] encodedPacket = packetBuilder.encode(packet);
        Packet decodedPacket = packetBuilder.decode(encodedPacket);
        Assertions.assertEquals(packet, decodedPacket);
    }

    @Test
    public void shouldThrowIfHeaderInvalid() {

        Exception exception = Assertions.assertThrows(RuntimeException.class, () -> {
            byte[] encodedPacket = packetBuilder.encode(packet);
            encodedPacket[1] = 0x0;
            Packet decodedPacket = packetBuilder.decode(encodedPacket);
        });

        String expectedMessage = "Header is not valid";
        String actualMessage = exception.getMessage();

        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void shouldThrowIfMessageInvalid() {

        Exception exception = Assertions.assertThrows(RuntimeException.class, () -> {
            byte[] encodedPacket = packetBuilder.encode(packet);
            encodedPacket[16] = 0x0;
            Packet decodedPacket = packetBuilder.decode(encodedPacket);
        });

        String expectedMessage = "Message is not valid";
        String actualMessage = exception.getMessage();

        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }
}