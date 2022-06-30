package org.example.fakes;

import org.example.processing.Decryptor;
import org.example.processing.Encryptor;
import org.example.processing.Processor;

public class FakeServer {
   public static void main(String[] args) {
      new FakeReceiver().receiveMessage(1);
      new Decryptor().decrypt();
      new Processor().process();
      new Encryptor().encrypt();
      new FakeSender().sendMessage();
   }
}
