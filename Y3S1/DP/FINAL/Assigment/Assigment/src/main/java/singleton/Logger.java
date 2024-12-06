package singleton;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


//implementation logger class for singleton pattern
public class Logger {
    private static Logger instance;
    private String logFile;

    private Logger(String logFile) {
        this.logFile = logFile;
    }

    public static synchronized Logger getInstance() {
        if (instance == null) {
            instance = new Logger("bank_log.txt");
        }
        return instance;
    }

    public synchronized void log(String message) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String logMessage = timestamp + " - " + message;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFile, true))) {
            writer.write(logMessage);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Logging error: " + e.getMessage());
        }
    }
}
