import org.junit.jupiter.api.Test;
import singleton.Logger;

import static org.junit.jupiter.api.Assertions.*;


//singleton unit test
public class LoggerSingletonTest {

    @Test
    public void testSingletonInstance() {
        Logger logger1 = Logger.getInstance();
        Logger logger2 = Logger.getInstance();

        assertSame(logger1, logger2);
    }
}

