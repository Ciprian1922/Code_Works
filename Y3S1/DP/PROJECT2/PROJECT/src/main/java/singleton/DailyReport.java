package singleton;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Handles generating and saving daily reports for the bank.
 */
public class DailyReport {

    /**
     * Saves the provided report to a file.
     */
    public static void saveReportToFile(String report, String fileName) {
        try (FileWriter writer = new FileWriter(fileName, true)) {
            writer.write(report);
            writer.write("\n");
            AppLogger.logInfo("Saved report to file: " + fileName); // SINGLETON LOGGER IMPLEMENTED
        } catch (IOException e) {
            AppLogger.logWarning("Failed to save report: " + e.getMessage()); // EXCEPTION HANDLING FOR LOGGING IMPLEMENTED
        }
        /**
         The AppLogger class is implemented as a singleton to provide a centralized logging mechanism for the application.
         It logs operations like saving reports or exceptions to a bank_operations.log file. The singleton ensures only one
         logger instance is used throughout the program, maintaining consistency.
         */
    }
}
