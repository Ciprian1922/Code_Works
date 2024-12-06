package singleton;

import java.util.logging.Logger;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;
import java.io.File;
import java.io.IOException;

/**
 * LOGGING FOR THE ENTIRE APPLICATION
 * LOGS INFORMATION TO A FILE CALLED bank_operations.log
 */
public class AppLogger {
    private static Logger logger = Logger.getLogger("BankLogger");

    // CONFIGURE LOGGER ONCE
    static {
        try {
            // DEBUG: Print the absolute path where the log file will be created
            File logFile = new File("bank_operations.log");
            System.out.println("Log file location: " + logFile.getAbsolutePath());

            // Configure logger with file handler
            FileHandler fileHandler = new FileHandler("src/bank_operations.log", true);// Append mode
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);

            // Avoid duplicating logs in the console
            logger.setUseParentHandlers(false);

        } catch (IOException e) {
            System.err.println("Failed to initialize logger: " + e.getMessage());
            e.printStackTrace(); // Print stack trace for debugging
        }
    }

    /**
     * Logs informational messages.
     * @param message The message to log
     */
    public static void logInfo(String message) {
        logger.info(message);
    }

    /**
     * Logs warning messages.
     * @param message The warning to log
     */
    public static void logWarning(String message) {
        logger.warning(message);
    }
}
