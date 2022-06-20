package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;

public class FakeProcessor implements IProcessor {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private final Store store = new Store();
    private static final IEncryptor encryptor = new Encryptor();

    public Store getStore() {
        return store;
    }

    @Override
    public void process(Packet packet) {
        try {
            Thread t = new Thread(() -> {
                try {
                    Message message = packet.getMessage();
                    execCommand(message);
                    Response response = new Response(ResponseType.OK);
                    byte[] messageData = objectMapper.writeValueAsBytes(response);
                    Message responseMessage = new Message(message.getCommandType(), message.getUserId(), messageData);
                    Packet responsePacket = new Packet(packet.getClientId(), packet.getPacketId() + 1, responseMessage);
                    encryptor.encrypt(responsePacket);
                    encryptor.encrypt(responsePacket);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
            t.start();
            t.join();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String execCommand(Message message) throws Exception {
        String result = "";
        synchronized (store) {
            switch (message.getCommandType()) {
                case CREATE_ARTICLE -> {
                    StoreCommands.CreateArticleBody body = objectMapper.readValue(message.getBody(), StoreCommands.CreateArticleBody.class);
                    store.addArticleToGroup(new Article(body.articleName, body.price, body.amount), body.groupName);
                }
                case CREATE_GROUP -> {
                    StoreCommands.CreateGroupBody body = objectMapper.readValue(message.getBody(), StoreCommands.CreateGroupBody.class);
                    store.addGroup(new Group(body.name));
                }
                case INCREMENT_ARTICLE -> {
                    StoreCommands.IncrementArticleBody body = objectMapper.readValue(message.getBody(), StoreCommands.IncrementArticleBody.class);
                    store.incrementArticle(body.articleName, body.increment);
//                    System.out.println(store.getAmountOfArticle(body.articleName));
                }
                case DECREMENT_ARTICLE -> {
                    StoreCommands.DecrementArticleBody body = objectMapper.readValue(message.getBody(), StoreCommands.DecrementArticleBody.class);
                    store.decrementArticle(body.articleName, body.decrement);
                }
                case SET_PRICE -> {
                    StoreCommands.SetPriceBody body = objectMapper.readValue(message.getBody(), StoreCommands.SetPriceBody.class);
                    store.setPriceForArticle(body.articleName, body.price);
                }
            }
        }
        return result;
    }

    public static void main(String[] args) throws Exception {
        FakeProcessor processor = new FakeProcessor();
        processor.getStore().addGroup(new Group("Food"));
        processor.getStore().addArticleToGroup(new Article("Banana", 10.4, 100), "Food");
        for (int i = 0; i < 10; i++) {
            byte[] messageData = objectMapper.writeValueAsBytes(
                    new StoreCommands.IncrementArticleBody("Banana", 10)
            );
            Message message = new Message(CommandType.INCREMENT_ARTICLE, 1, messageData);
            Packet packet = new Packet((byte) 1, i, message);
            processor.process(packet);
        }
    }
}
