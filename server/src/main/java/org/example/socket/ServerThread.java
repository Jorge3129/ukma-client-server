package org.example.socket;

import org.example.fakes.FakeReceiver;
import org.example.fakes.FakeSender;
import org.example.processing.*;

import java.io.*;
import java.net.Socket;

public class ServerThread extends Thread {

   private final Socket socket;
   private final BufferedReader in;
   private final PrintWriter out;

   public ServerThread(Socket s) throws IOException {
      socket = s;
      in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
      start();
   }

   public void run() {
      try {
         while (true) {
            String str = in.readLine();
            if (str.equals("END")) break;
            System.out.println("Request: " + str);
            new Receiver().receiveMessage(str.getBytes());
            new Decryptor().decrypt();
            new Processor().process();
            new Encryptor().encrypt();
            new Sender().sendMessage(out);
         }
         System.out.println("Closing server socket");
      } catch (IOException e) {
         System.err.println("IO Exception");
      } finally {
         try {
            socket.close();
         } catch (IOException e) {
            System.err.println("Socket is not closed ...");
         }
      }
   }
}
