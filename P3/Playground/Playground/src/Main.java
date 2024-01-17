import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static String currentUser;
    public static void main(String[] args) {
        ManagementSystem system = new ManagementSystem();
        boolean isLoadEnabled = isLoadEnabled(args);
        boolean isDev = isDevEnabled(args);
        boolean isDbEnabled = isDbEnabled(args);
        //obtain login credentials
        String loggedInUser = getUsername();
        String password = getPassword();

        //check if login was successful
        if (loggedInUser == null || password == null || !validateLogin(loggedInUser, password)) {
            //show an error message and return without further execution
            JOptionPane.showMessageDialog(null, "Login failed. Exiting the application.", "Login Failed", JOptionPane.ERROR_MESSAGE);
            return;
        }

        DefaultTableModel databaseModel;

        if (isLoadEnabled || isDbEnabled) {
            databaseModel = DatabaseHandler.fetchDataFromDatabase("ID", loggedInUser, password);

            if ("averageman".equals(loggedInUser)) {
                //filter employees based on the Role for averageman
                databaseModel = filterEmployeesByRegion(databaseModel, "Romania");
            }
        } else {
            databaseModel = new DefaultTableModel(new Object[]{"ID", "Name", "Age", "Role", "Married", "Region"}, 0);
        }

        List<Employee> employees = getEmployeesFromTableModel(databaseModel);
        system.addEmployees(employees);

        //create and show the GUI
        SwingUtilities.invokeLater(() -> {
            EmployeeManagementGUI gui = new EmployeeManagementGUI(system, isDev, isLoadEnabled, isDbEnabled);
            gui.createAndShowGUI();
        });

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (isLoadEnabled) {
                InputDevice.saveEmployeesToFile(system.getEmployees(), isLoadEnabled);
            }
        }));
    }



    private static List<Employee> getEmployeesFromTableModel(DefaultTableModel model) {
        List<Employee> employees = new ArrayList<>();

        for (int row = 0; row < model.getRowCount(); row++) {
            int id = (int) model.getValueAt(row, 0);
            String name = (String) model.getValueAt(row, 1);
            int age = (int) model.getValueAt(row, 2);
            Role role = Role.valueOf((String) model.getValueAt(row, 3));
            boolean isMarried = (boolean) model.getValueAt(row, 4);
            Region region = Region.valueOf((String) model.getValueAt(row, 5));

            Employee employee = new Employee(id, name, age, role, isMarried, region);
            employees.add(employee);
        }

        return employees;
    }

    private boolean showLoginDialog() {
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        Object[] message = {
                "Username:", usernameField,
                //"Password:", passwordField
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Login", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            String username = usernameField.getText();
            char[] passwordChars = passwordField.getPassword();
            String password = new String(passwordChars);

            //checking the login credentials
            if (validateLogin(username, password)) {
                currentUser = username;
                return true; //login successful
            } else {
                JOptionPane.showMessageDialog(null, "Invalid username or password", "Login Failed", JOptionPane.ERROR_MESSAGE);
                return false; //login failed
            }
        } else {
            return false; //user clicked Cancel
        }
    }

    //checking the arguments state
    private static boolean isDevEnabled(String[] args) {
        for (String arg : args) {
            if ("dev".equals(arg)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isLoadEnabled(String[] args) {
        for (String arg : args) {
            if ("load".equals(arg)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isDbEnabled(String[] args) {
        for (String arg : args) {
            if ("db".equals(arg)) {
                return true;
            }
        }
        return false;
    }

    private static DefaultTableModel filterEmployeesByRegion(DefaultTableModel model, String region) {
        DefaultTableModel filteredModel = new DefaultTableModel(new Object[]{"ID", "Name", "Age", "Role", "Married", "Region"}, 0);

        for (int i = 0; i < model.getRowCount(); i++) {
            String employeeRegion = (String) model.getValueAt(i, 5); //assuming Region is at index 5
            if (region.equals(employeeRegion)) {
                Object[] rowData = new Object[6];
                for (int j = 0; j < 6; j++) {
                    rowData[j] = model.getValueAt(i, j);
                }
                filteredModel.addRow(rowData);
            }
        }

        //clear the original model and add filtered rows
        model.setRowCount(0);
        for (int i = 0; i < filteredModel.getRowCount(); i++) {
            Object[] rowData = new Object[6];
            for (int j = 0; j < 6; j++) {
                rowData[j] = filteredModel.getValueAt(i, j);
            }
            model.addRow(rowData);
        }

        return model;
    }
    private static DefaultTableModel getEmployeesFromRomania(DefaultTableModel model) {
        DefaultTableModel romaniaModel = new DefaultTableModel(new Object[]{"ID", "Name", "Age", "Role", "Married", "Region"}, 0);

        for (int i = 0; i < model.getRowCount(); i++) {
            String employeeRegion = (String) model.getValueAt(i, 5); //assuming Region is at index 5
            if ("Romania".equals(employeeRegion)) {
                Object[] rowData = new Object[6];
                for (int j = 0; j < 6; j++) {
                    rowData[j] = model.getValueAt(i, j);
                }
                romaniaModel.addRow(rowData);
            }
        }

        return romaniaModel;
    }
    private static void filterEmployeesByRole(DefaultTableModel model, String role) {
        for (int i = model.getRowCount() - 1; i >= 0; i--) {
            String employeeRole = (String) model.getValueAt(i, 3); //assuming Role is at index 3
            if (!role.equals(employeeRole)) {
                //remove rows that don't match the specified role
                model.removeRow(i);
            }
        }
    }
    private static String getUsername() {
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        Object[] message = {
                "Username:", usernameField,
                //"Password:", passwordField
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Login", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            return usernameField.getText();
        }
        return null;
    }

    private static String getPassword() {
        JPasswordField passwordField = new JPasswordField();
        Object[] message = {
                "Password:", passwordField
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Login", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            return new String(passwordField.getPassword());
        }
        return null;
    }
    private static boolean validateLogin(String username, String password) {
        if ("angajat".equals(username) && "sefdesef".equals(password)) {
            currentUser = username;
            return true;
        } else if ("averageman".equals(username) && "simple".equals(password)) {
            currentUser = username;
            return true;
        }
        JOptionPane.showMessageDialog(null, "Invalid username or password", "Login Failed", JOptionPane.ERROR_MESSAGE);
        return false;
    }
}
