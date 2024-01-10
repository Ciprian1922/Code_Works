import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    public static void main(String[] args) {
        ManagementSystem system = new ManagementSystem();
        boolean isLoadEnabled = isLoadEnabled(args);
        boolean isDev = isDevEnabled(args);

        if (isLoadEnabled) {
            system.addEmployees(InputDevice.loadEmployeesFromFile()); // Load employees from file if "load" arg is enabled
        } else {
            system.addEmployees(InputDevice.defaultEmployees);
        }

        // Automatically save the employee table in "emp.txt" when the app is closed
        SwingUtilities.invokeLater(() -> {
            EmployeeManagementGUI gui = new EmployeeManagementGUI(system, isDev, isLoadEnabled);
            gui.createAndShowGUI();
        });

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (isLoadEnabled) {
                InputDevice.saveEmployeesToFile(system.getEmployees(), isLoadEnabled);
            }
        }));
    }

    // checking the arguments state
    private static boolean isDevEnabled(String[] args) {
        for (String arg : args) {
            if (arg.equals("dev")) {
                return true;
            }
        }
        return false;
    }

    private static boolean isLoadEnabled(String[] args) {
        for (String arg : args) {
            if (arg.equals("load")) {
                return true;
            }
        }
        return false;
    }

}
