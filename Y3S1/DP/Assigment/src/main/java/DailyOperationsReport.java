import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
public class DailyOperationsReport {
    private List<String> operations;
    private LocalDate reportDate;

    public DailyOperationsReport() {
        operations = new ArrayList<>();
        reportDate = LocalDate.now();
    }

    public void addOperation(String operation) {
        operations.add(operation);
    }

    public void generateReport() {
        System.out.println("Daily Operations Report for " + reportDate);
        for (String operation : operations) {
            System.out.println(operation);
        }
    }
}
