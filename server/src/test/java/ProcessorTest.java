import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ProcessorTest {

    @Test
    public void shouldIncrementCorrectly() throws Exception {
        FakeProcessor processor = new FakeProcessor();
        ObjectMapper objectMapper = new ObjectMapper();
        processor.getStore().addGroup(new Group("Food"));
        int initialAmount = 100;
        int increment = 10;
        int iterations = 10;
        Article article = new Article("Banana", 10.4, initialAmount);
        processor.getStore().addArticleToGroup(article, "Food");
        for (int i = 0; i < iterations; i++) {
            byte[] messageData = objectMapper.writeValueAsBytes(
                    new StoreCommands.IncrementArticleBody("Banana", increment)
            );
            Message message = new Message(CommandType.INCREMENT_ARTICLE, 1, messageData);
            Packet packet = new Packet((byte) 1, i, message);
            processor.process(packet);
        }
        int expectedResult = initialAmount + iterations * increment;
        Assertions.assertEquals(expectedResult, processor.getStore().getAmountOfArticle(article.getName()));
    }

    @Test
    public void shouldDecrementCorrectly() throws Exception {
        FakeProcessor processor = new FakeProcessor();
        ObjectMapper objectMapper = new ObjectMapper();
        processor.getStore().addGroup(new Group("Food"));
        int initialAmount = 200;
        int decrement = 10;
        int iterations = 10;
        Article article = new Article("Banana", 10.4, initialAmount);
        processor.getStore().addArticleToGroup(article, "Food");
        for (int i = 0; i < iterations; i++) {
            byte[] messageData = objectMapper.writeValueAsBytes(
                    new StoreCommands.DecrementArticleBody("Banana", decrement)
            );
            Message message = new Message(CommandType.DECREMENT_ARTICLE, 1, messageData);
            Packet packet = new Packet((byte) 1, i, message);
            processor.process(packet);
        }
        int expectedResult = initialAmount - iterations * decrement;
        Assertions.assertEquals(expectedResult, processor.getStore().getAmountOfArticle(article.getName()));
    }
}
