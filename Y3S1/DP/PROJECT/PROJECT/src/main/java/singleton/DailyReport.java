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
            AppLogger.logInfo("Saved report to file: " + fileName); //LOGGING FUNCTIONALITY
        } catch (IOException e) {
            AppLogger.logWarning("Failed to save report: " + e.getMessage()); //LOGGING EXCEPTION HANDLING
        }
    }
}
