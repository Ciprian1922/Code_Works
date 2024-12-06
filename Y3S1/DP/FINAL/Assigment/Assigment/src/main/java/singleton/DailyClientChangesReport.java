package singleton;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

public class DailyClientChangesReport {
    private List<String> clientChanges;
    private LocalDate reportDate;

    public DailyClientChangesReport() {
        clientChanges = new ArrayList<>();
        reportDate = LocalDate.now();
    }

    public void addClientChange(String change) {
        clientChanges.add(change);
    }

    public void generateReport() {
        System.out.println("Daily bank.Client Changes Report for " + reportDate);
        for (String change : clientChanges) {
            System.out.println(change);
        }
    }
}
