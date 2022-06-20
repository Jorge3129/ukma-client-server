package org.example;

import java.util.concurrent.*;

public class Decryptor implements IDecryptor {
    private static PacketBuilder packetBuilder = new PacketBuilder();
    private static IProcessor processor = new FakeProcessor();
    ExecutorService WORKER_THREAD_POOL = Executors.newFixedThreadPool(10);
    CompletionService<String> service
            = new ExecutorCompletionService<>(WORKER_THREAD_POOL);

    @Override
    public void decrypt(byte[] data) {
        new Thread(() -> {
            Packet result = packetBuilder.decode(data);
//            System.out.println(result);
            processor.process(result);
        }).start();
    }

    public void awaitTerminationAfterShutdown(ExecutorService threadPool) {
        threadPool.shutdown();
        try {
            if (!threadPool.awaitTermination(60, TimeUnit.SECONDS)) {
                threadPool.shutdownNow();
            }
        } catch (InterruptedException ex) {
            threadPool.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
