package singleton;

import java.util.logging.Logger;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;
import java.io.IOException;

/**
  logging for the entire application
  logs information to a file called bank_operations.log
 */
public class AppLogger {
    private static Logger logger = Logger.getLogger("BankLogger");

    static {
        try {
            FileHandler fileHandler = new FileHandler("bank_operations.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
            logger.setUseParentHandlers(false);
        } catch (IOException e) {
            System.err.println("Failed to initialize logger: " + e.getMessage());
        }
    }

    public static void logInfo(String message) {
        logger.info(message);
    }

    public static void logWarning(String message) {
        logger.warning(message);
    }
}
