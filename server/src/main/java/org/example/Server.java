package org.example;

import org.example.processing.*;

public class Server {
   public static void main(String[] args) {
      new Receiver().receiveMessage(1);
      new Decryptor().decrypt();
      new Processor().process();
      new Encryptor().encrypt();
      new Sender().sendMessage();
   }
}
