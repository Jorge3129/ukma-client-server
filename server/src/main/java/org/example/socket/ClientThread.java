package org.example.socket;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Date;

class ClientThread extends Thread {

   private Socket socket;
   private BufferedReader in;
   private PrintWriter out;
   private static int counter = 0;
   private final int id = counter++;
   private static int threadCount = 0;

   public ClientThread(InetAddress addr, int port) {
      System.out.println("Started client " + id);
      threadCount++;
      try {
         socket = new Socket(addr, port);
      } catch (IOException e) {
         System.err.println("Connection to server failed");
      }
      try {
         in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
         out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
         start();
      } catch (IOException e) {
         try {
            socket.close();
         } catch (IOException e2) {
            System.err.println("Сокет не закрито");
         }
      }
   }

   public static int threadCount() {
      return threadCount;
   }

   public void run() {
      try {
         for (int i = 0; i < 5; i++) {
            out.println("Client " + id + ": " + i + " час відправки: " + new Date().getTime());
            String str = in.readLine();
            System.out.println(str + " час отримання: " + new Date().getTime());
         }
         out.println("END");
      } catch (IOException e) {
         System.err.println("IO Exception");
      } finally {
         try {
            socket.close();
         } catch (IOException e) {
            System.err.println("Socket not closed");
         }
//         threadCount--; // Завершуємо цей потік
      }
   }
}