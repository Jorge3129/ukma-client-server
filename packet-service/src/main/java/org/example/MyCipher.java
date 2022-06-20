package org.example;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.security.*;
import java.util.Arrays;

public class MyCipher {
    private final Cipher cipher;
    private final Key cipherKey;
    private final IvParameterSpec ivspec;
    private final int BLOCK_SIZE = 16;

    public MyCipher(String algorithm) throws NoSuchPaddingException, NoSuchAlgorithmException {
        this.cipher = Cipher.getInstance(algorithm);
        this.cipherKey = MyCipher.generateKey();
        this.ivspec = MyCipher.generateIv();
    }

    public byte[] encrypt(byte[] input)
            throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException, InvalidAlgorithmParameterException {
        this.cipher.init(Cipher.ENCRYPT_MODE, this.cipherKey, this.ivspec);
//        byte[] paddedInput = addPadding(input);
//        System.out.println(paddedInput.length);
//        byte[] result = cipher.doFinal(paddedInput);
//        System.out.println(result.length);
        return cipher.doFinal(input);
    }

    public byte[] decrypt(byte[] input)
            throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException, InvalidAlgorithmParameterException {
        this.cipher.init(Cipher.DECRYPT_MODE, this.cipherKey, this.ivspec);
//        System.out.println(input.length);
//        byte[] paddedResult = cipher.doFinal(input);
        return cipher.doFinal(input);
    }

    private byte[] addPadding(byte[] input) {
        int length = input.length % this.BLOCK_SIZE == 0
                ? input.length + BLOCK_SIZE
                : calculatePaddedLength(input.length, BLOCK_SIZE);
        byte[] result = new byte[length];
        System.arraycopy(input, 0, result, 0, input.length);
        for (int i = input.length; i < result.length; i++) {
            result[i] = 0;
        }
        int paddingSize = result.length - input.length;
        result[result.length - 1] = (byte) paddingSize;
        return result;
    }

    private byte[] removePadding(byte[] input) {
        int paddingSize = input[input.length - 1];
        byte[] result = new byte[input.length - paddingSize];
        System.arraycopy(input, 0, result, 0, result.length);
        return result;
    }

    private int calculatePaddedLength(int initialLength, int blockSize) {
        return blockSize * (int) Math.ceil((double) initialLength / blockSize);
    }

    private static Key generateKey()
            throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128);
        return keyGenerator.generateKey();
    }

    public static IvParameterSpec generateIv() {
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        return new IvParameterSpec(iv);
    }

    public static void main(String[] args) {
        try {
            String algorithm = "AES";
            MyCipher cipher = new MyCipher(algorithm);
            Message message = new Message(CommandType.CREATE_ARTICLE, 1, new byte[]{1, 2, 3});
            Packet packet = new Packet((byte) 1, 1, message);
            byte[] data = new ObjectMapper().writeValueAsBytes(packet);
            byte[] encrypted = cipher.encrypt(data);
            byte[] decrypted = cipher.decrypt(encrypted);
            System.out.println(Arrays.toString(decrypted));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}