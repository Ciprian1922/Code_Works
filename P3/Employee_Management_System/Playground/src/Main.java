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
            system.addEmployees(InputDevice.loadEmployeesFromFile()); // Load employees from file
        } else {
            system.addEmployees(InputDevice.defaultEmployees);
            //system.addEmployees(InputDevice.defaultEmployees);
            //system.addEmployees(InputDevice.defaultEmployees);
            //system.addEmployees(InputDevice.defaultEmployees);
        }

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

    public static void aici(String[] args) {
        InputDevice inputdevice = new InputDevice();
        OutputDevice outputdevice = new OutputDevice();
        Brain brain = new Brain(inputdevice, outputdevice);

        // Check if "dev" is enabled
        boolean isDev = isDevEnabled(args);
        brain.run(isDev);
    }

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
