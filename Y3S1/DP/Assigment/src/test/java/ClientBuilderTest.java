import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ClientBuilderTest {

    @Test
    public void testClientBuilder() {

        Client client = new Client.Builder()
                .withName("Alice")
                .withAddress("Wonderland")
                .build();

        assertEquals("Alice", client.getName());
        assertEquals("Wonderland", client.getAddress());
        assertTrue(client.toString().contains("name='Alice'"));
        assertTrue(client.toString().contains("address='Wonderland'"));
    }
}


