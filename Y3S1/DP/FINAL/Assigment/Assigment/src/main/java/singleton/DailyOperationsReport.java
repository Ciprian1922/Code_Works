package singleton;

import java.util.ArrayList;
import java.util.List;

public class DailyOperationsReport {
    private List<String> operations;

    public DailyOperationsReport() {
        this.operations = new ArrayList<>();
    }

    // Accept a String for operation descriptions
    public void addOperation(String operation) {
        operations.add(operation);
    }

    public void generateReport() {
        System.out.println("Generating Daily operations.Operations Report...");
        for (String operation : operations) {
            System.out.println(operation);
        }
    }

    public List<String> getOperations() {
        return operations;
    }
}
