import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    public static void main(String[] args) {
        ManagementSystem system = new ManagementSystem();
        system.addEmployees(InputDevice.defaultEmployees);
        system.addEmployees(InputDevice.defaultEmployees);

        // Check if "dev" mode is enabled
        boolean isDev = isDevEnabled(args);

        SwingUtilities.invokeLater(() -> {
            EmployeeManagementGUI gui = new EmployeeManagementGUI(system, isDev);
            gui.createAndShowGUI();
        });
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
}
