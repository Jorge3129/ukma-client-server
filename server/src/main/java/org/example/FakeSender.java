package org.example;

import org.example.interfaces.INetAddress;
import org.example.interfaces.ISender;

public class FakeSender implements ISender {
    @Override
    public void sendMessage(byte[] message, INetAddress address) {
        new Thread(() -> {
            //System.out.println(new String(message));
        }).start();
    }
}
