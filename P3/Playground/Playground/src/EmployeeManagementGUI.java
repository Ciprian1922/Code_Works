import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Scanner;
import javax.swing.table.DefaultTableModel;
import java.io.IOException;

public class EmployeeManagementGUI implements Addable {
    private ManagementSystem system;
    private DefaultTableModel tableModel;
    private boolean isDevEnabled;
    private boolean isLoggedIn = false;
    private String currentUser = null;
    public boolean validateEmployeeInput(int id, String name, int age, Role function, boolean isMarried, Region region) {
        // Your validation logic here
        if (id <= 0) {
            JOptionPane.showMessageDialog(null, "Invalid ID. Please enter a positive integer.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true; // If all validations pass
    }

    public EmployeeManagementGUI(ManagementSystem system, boolean isDevEnabled, boolean isLoadEnabled) {
        this.system = system;
        this.tableModel = new DefaultTableModel(new Object[]{"ID", "Name", "Age", "Function", "Married", "Region"}, 0);
        // Populate the table model with the employees from the ManagementSystem
        for (Employee employee : system.getEmployees()) {
            tableModel.addRow(new Object[]{employee.getId(), employee.getName(), employee.getAge(), employee.getFunction(), employee.isMarried(), employee.getRegion()});
        }
        this.isDevEnabled = isDevEnabled;

    }

    public Role getNextRole(Role currentRole) {
        Role[] roles = Role.values();
        for (int i = 0; i < roles.length - 1; i++) {
            if (roles[i] == currentRole) {
                return roles[i + 1];
            }
        }
        return null; // If the current role is the highest, return null
    }

    public void createAndShowGUI() {
        JFrame frame = new JFrame("Employee Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(InputDevice.width, InputDevice.height);

        frame.setLayout(new BorderLayout());

        JTable employeeTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(employeeTable);

        JButton addEmployeeButton = new JButton("Add Employee");
        addEmployeeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Create input fields for employee details
                JTextField idField = new JTextField();
                JTextField nameField = new JTextField();
                JTextField ageField = new JTextField();
                JComboBox<Role> functionCombo = new JComboBox<>(Role.values());
                String[] maritalStatusOptions = {"Married", "Single"};
                JComboBox<String> maritalStatusCombo = new JComboBox<>(maritalStatusOptions);
                JComboBox<Region> regionCombo = new JComboBox<>(Region.values());

                JPanel panel = new JPanel(new GridLayout(7, 2));
                panel.add(new JLabel("ID:"));
                panel.add(idField);
                panel.add(new JLabel("Name:"));
                panel.add(nameField);
                panel.add(new JLabel("Age:"));
                panel.add(ageField);
                panel.add(new JLabel("Function:"));
                panel.add(functionCombo);
                panel.add(new JLabel("Marital Status:"));
                panel.add(maritalStatusCombo);
                panel.add(new JLabel("Region:"));
                panel.add(regionCombo);

                int result = JOptionPane.showConfirmDialog(frame, panel, "Add employee",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                // Inside the "Add Employee" button ActionListener
                if (result == JOptionPane.OK_OPTION) {
                    String idText = idField.getText();
                    String ageText = ageField.getText();

                    if (idText.isEmpty() || ageText.isEmpty()) {
                        JOptionPane.showMessageDialog(frame, "ID and Age must not be empty!", "Error", JOptionPane.ERROR_MESSAGE);
                        return; // Exit the method early
                    }

                    boolean isValidInput = true;

                    try {
                        int id = Integer.parseInt(idText);

                        // Check if an employee with the given ID already exists
                        if (system.employeeExists(id)) {
                            JOptionPane.showMessageDialog(frame, "An employee with the same ID already exists.", "Error", JOptionPane.ERROR_MESSAGE);
                            return; // Exit the method early
                        }

                        String name = readNameFromGui(frame, nameField.getText());
                        int age = Integer.parseInt(ageText);
                        Role function = (Role) functionCombo.getSelectedItem();
                        boolean isMarried = maritalStatusCombo.getSelectedItem().equals("Married");
                        Region region = (Region) regionCombo.getSelectedItem();

                        // Use the validateEmployeeInput method for validation
                        isValidInput = validateEmployeeInput(id, name, age, function, isMarried, region);

                        if (isValidInput) {
                            // Create a new employee and add it to the system
                            Employee newEmployee = new Employee(id, name, age, function, isMarried, region);
                            system.addEmployee(newEmployee);

                            // Add the new employee to the table
                            tableModel.addRow(new Object[]{id, name, age, function, isMarried, region});
                        }

                    } catch (NumberFormatException ex) {
                        isValidInput = false;
                        JOptionPane.showMessageDialog(frame, "Invalid input for ID or Age. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
                    }

                    if (!isValidInput) {
                        JOptionPane.showMessageDialog(frame, "Invalid input. Please check the entered values.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        JButton editEmployeeButton = new JButton("Edit Employee");
        editEmployeeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = employeeTable.getSelectedRow();

                if (selectedRow != -1) {
                    int id = (int) tableModel.getValueAt(selectedRow, 0);

                    // Find the employee with the selected ID
                    Employee selectedEmployee = null;
                    for (Employee employee : system.getEmployees()) {
                        if (employee.getId() == id) {
                            selectedEmployee = employee;
                            break;
                        }
                    }

                    if (selectedEmployee != null) {
                        // Create input fields for all employee details
                        JTextField idField = new JTextField(String.valueOf(selectedEmployee.getId()));
                        JTextField nameField = new JTextField(selectedEmployee.getName());
                        JTextField ageField = new JTextField(String.valueOf(selectedEmployee.getAge()));
                        JComboBox<Role> functionCombo = new JComboBox<>(Role.values());
                        functionCombo.setSelectedItem(selectedEmployee.getFunction());
                        String[] maritalStatusOptions = {"Married", "Single"};
                        JComboBox<String> maritalStatusCombo = new JComboBox<>(maritalStatusOptions);
                        maritalStatusCombo.setSelectedItem(selectedEmployee.isMarried() ? "Married" : "Single");
                        JComboBox<Region> regionCombo = new JComboBox<>(Region.values());
                        regionCombo.setSelectedItem(selectedEmployee.getRegion());

                        JPanel panel = new JPanel(new GridLayout(7, 2));
                        panel.add(new JLabel("ID:"));
                        panel.add(idField);
                        panel.add(new JLabel("Name:"));
                        panel.add(nameField);
                        panel.add(new JLabel("Age:"));
                        panel.add(ageField);
                        panel.add(new JLabel("Function:"));
                        panel.add(functionCombo);
                        panel.add(new JLabel("Marital Status:"));
                        panel.add(maritalStatusCombo);
                        panel.add(new JLabel("Region:"));
                        panel.add(regionCombo);

                        int result = JOptionPane.showConfirmDialog(frame, panel, "Edit Employee",
                                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                        if (result == JOptionPane.OK_OPTION) {
                            String idText = idField.getText();
                            String ageText = ageField.getText();

                            if (idText.isEmpty() || ageText.isEmpty()) {
                                JOptionPane.showMessageDialog(frame, "ID and Age must not be empty!", "Error", JOptionPane.ERROR_MESSAGE);
                                return; // Exit the method early
                            }

                            boolean isValidInput = true;

                            try {
                                int editedId = Integer.parseInt(idText);
                                String editedName = readNameFromGui(frame, nameField.getText());
                                int editedAge = Integer.parseInt(ageText);
                                Role editedFunction = (Role) functionCombo.getSelectedItem();
                                boolean isMarried = maritalStatusCombo.getSelectedItem().equals("Married");
                                Region editedRegion = (Region) regionCombo.getSelectedItem();

                                // Check if any of the fields are invalid
                                if (editedName == null) {
                                    isValidInput = false;
                                }

                                if (isValidInput) {
                                    // Update the selected employee's details
                                    selectedEmployee.setId(editedId);
                                    selectedEmployee.setName(editedName);
                                    selectedEmployee.setAge(editedAge);
                                    selectedEmployee.setFunction(String.valueOf(editedFunction));
                                    selectedEmployee.setMarried(isMarried);
                                    selectedEmployee.setRegion(editedRegion);

                                    // Update the table
                                    tableModel.setValueAt(editedId, selectedRow, 0);
                                    tableModel.setValueAt(editedName, selectedRow, 1);
                                    tableModel.setValueAt(editedAge, selectedRow, 2);
                                    tableModel.setValueAt(editedFunction, selectedRow, 3);
                                    tableModel.setValueAt(isMarried, selectedRow, 4);
                                    tableModel.setValueAt(editedRegion, selectedRow, 5);
                                }
                            } catch (NumberFormatException ex) {
                                isValidInput = false;
                                JOptionPane.showMessageDialog(frame, "Invalid input for ID or Age. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
                            }

                            if (!isValidInput) {
                                JOptionPane.showMessageDialog(frame, "Invalid input. Please check the entered values.", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    }
                }
            }
        });

        JButton removeEmployeeButton = new JButton("Remove Employee");
        if (!isDevEnabled) {
            removeEmployeeButton.setEnabled(false);
        }
        removeEmployeeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = employeeTable.getSelectedRow();
                if (selectedRow != -1) {
                    // Remove the selected employee from the system and the table
                    int id = (int) tableModel.getValueAt(selectedRow, 0);
                    for (Employee employee : system.getEmployees()) {
                        if (employee.getId() == id) {
                            system.removeEmployee(employee);
                            tableModel.removeRow(selectedRow);
                            break;
                        }
                    }
                }
            }
        });

        // Add the "Promote Employee" button and its action listener
        JButton promoteEmployeeButton = new JButton("Promote Employee");
        promoteEmployeeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = employeeTable.getSelectedRow();

                if (selectedRow != -1) {
                    // Get the selected employee
                    int id = (int) tableModel.getValueAt(selectedRow, 0);
                    Employee selectedEmployee = system.getEmployeeById(id);

                    if (selectedEmployee != null) {
                        // Check if the employee is upgradable
                        if (selectedEmployee.isUpgradable()) {
                            // Perform the upgrade
                            selectedEmployee.upgrade();

                            // Update the table to reflect the change
                            tableModel.setValueAt(selectedEmployee.getFunction(), selectedRow, 3); // Assuming the column index for Function is 3
                        } else {
                            JOptionPane.showMessageDialog(frame, "The employee cannot be upgraded.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
        });

        JButton readFromConsoleButton = new JButton("Read from console");
        readFromConsoleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Read employee details from the console
                    int id = readIntFromConsole("Provide the ID of the employee: ");
                    String name = readNameFromConsole("Provide the name of the employee: ");
                    int age = readAgeFromConsole("Provide the age of the employee: ");
                    Role function = Role.valueOf(readRoleFromConsole("Provide the Function of the employee: (Intern, Junior, Associate, Intermediate, Senior, Lead, Team_Lead, Director, Ceo) "));
                    boolean isMarried = readYesNoFromConsole("Is the employee married? (y/n): ");
                    Region region = readLocationFromConsole("Provide the Region of the employee: (Romania, Germany, Italy, Spain, Sweden)");

                    // Create a new employee and add it to the system
                    Employee newEmployee = new Employee(id, name, age, function, isMarried, region);
                    system.addEmployee(newEmployee);

                    // Add the new employee to the table
                    tableModel.addRow(new Object[]{id, name, age, function, isMarried, region});
                    //SQL THING THAT ADDS THE EMPLOYEE
                    // Notify that the employee has been read successfully
                    OutputDevice.write("Employee read successfully");

                    // Enable the button again
                    readFromConsoleButton.setEnabled(true);

                } catch (NumberFormatException ex) {
                    OutputDevice.write("Error: Invalid input. Please enter a valid number.");
                } catch (IllegalArgumentException ex) {
                    OutputDevice.write("Error: Invalid input. Please enter a valid value.");
                }
            }
        });

        // Add the "Battle" button and its action listener
        JButton battleButton = new JButton("Battle");
        battleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create input fields for employee selection
                JComboBox<Employee> employee1Combo = new JComboBox<>(system.getEmployees().toArray(new Employee[0]));
                JComboBox<Employee> employee2Combo = new JComboBox<>(system.getEmployees().toArray(new Employee[0]));

                JPanel panel = new JPanel(new GridLayout(3, 2));
                panel.add(new JLabel("Select Employee 1:"));
                panel.add(employee1Combo);
                panel.add(new JLabel("Select Employee 2:"));
                panel.add(employee2Combo);

                int result = JOptionPane.showConfirmDialog(frame, panel, "Battle",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                // Inside the "Battle" button ActionListener
                if (result == JOptionPane.OK_OPTION) {
                    Employee employee1 = (Employee) employee1Combo.getSelectedItem();
                    Employee employee2 = (Employee) employee2Combo.getSelectedItem();

                    // Determine the winner and loser (randomly for simplicity)
                    Employee winner = (Math.random() < 0.5) ? employee1 : employee2;
                    Employee loser = (winner == employee1) ? employee2 : employee1;

                    // Display the winner in a new popup
                    JOptionPane.showMessageDialog(frame, winner.getName() + " won the battle!", "Battle Result", JOptionPane.INFORMATION_MESSAGE);

                    // Remove the loser employee from the system and the table
                    system.removeEmployee(loser);
                    for (int i = 0; i < tableModel.getRowCount(); i++) {
                        if ((int) tableModel.getValueAt(i, 0) == loser.getId()) {
                            tableModel.removeRow(i);
                            break;
                        }
                    }
                }
            }
        });

        JButton uploadToDatabaseButton = new JButton("Upload to Database");
        uploadToDatabaseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isLoggedIn) {
                    // If not logged in, show the login dialog
                    if (showLoginDialog()) {
                        // If login is successful, set the flag to true
                        isLoggedIn = true;
                    }
                }

                if (isLoggedIn) {
                    // If logged in, proceed with the data upload
                    uploadDataToDatabase();
                }
            }
        });

        // Add the "Stats" button and its action listener
        JButton statsButton = new JButton("DB_Content");
        statsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Show the employee list window as a non-blocking dialog
                EmployeeListWindow employeeListWindow = new EmployeeListWindow();
                employeeListWindow.showWindow();
            }
        });

        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Reset the isLoggedIn variable and set currentUser to null
                isLoggedIn = false;
                currentUser = null;
                JOptionPane.showMessageDialog(null, "Logged out successfully.", "Logout", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        frame.add(tableScrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addEmployeeButton);
        buttonPanel.add(editEmployeeButton);
        buttonPanel.add(removeEmployeeButton);
        buttonPanel.add(promoteEmployeeButton);
        buttonPanel.add(readFromConsoleButton);
        buttonPanel.add(battleButton);
        buttonPanel.add(uploadToDatabaseButton);
        buttonPanel.add(statsButton);
        buttonPanel.add(logoutButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }
    private String readFromConsole(String prompt) {
        System.out.print(prompt);
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        OutputDevice.write("User input: " + input);
        return input;
    }

    private boolean readYesNoFromConsole(String prompt) {
        while (true) {
            String response = readFromConsole(prompt).toLowerCase();

            if (response.equals("y") || response.equals("n")) {
                return response.equals("Yes");
            } else {
                OutputDevice.write("Error: Invalid input. Please enter 'y' or 'n'.");
            }
        }
    }

    private int readIntFromConsole(String prompt) {
        while (true) {
            try {
                int id = Integer.parseInt(readFromConsole(prompt));

                // Check if the ID already exists
                if (system.employeeExists(id) && id > 0) {
                    OutputDevice.write("Error: Employee with ID " + id + " already exists. Please enter a unique ID.");
                } else {
                    return id;
                }
            } catch (NumberFormatException ex) {
                OutputDevice.write("Error: Invalid input. Please enter a valid number.");
            }
        }
    }

    private String readNameFromConsole(String prompt) {
        while (true) {
            String name = readFromConsole(prompt);

            // Remove extra spaces and reduce them to a single space
            name = name.replaceAll("\\s+", " ").trim();

            // Allow letters, spaces, and hyphens in the name
            if (name.matches("[a-zA-Z\\s-]+") && name.length() < 100) {
                return name;
            } else {
                OutputDevice.write("Error: Invalid characters in the name. Please enter a valid name.");
            }
        }
    }


    private int readAgeFromConsole(String prompt) {
        while (true) {
            try {
                int age = Integer.parseInt(readFromConsole(prompt));
                if (age >= 1 && age <= 200) {
                    return age;
                } else {
                    OutputDevice.write("Error: Invalid input. Please enter an age between 1 and 200.");
                }
            } catch (NumberFormatException ex) {
                OutputDevice.write("Error: Invalid input. Please enter a valid number for the age.");
            }
        }
    }

    private String readRoleFromConsole(String prompt) {
        while (true) {
            String function = readFromConsole(prompt);

            // Check if the function is present in the Role enum
            try {
                Role.valueOf(function);
                return function;
            } catch (IllegalArgumentException ex) {
                OutputDevice.write("Error: " + ex.getMessage() +
                        "\nAllowed values: (Intern, Junior, Associate, Intermediate, Senior, Lead, Team_Lead, Director, Ceo)");
            }
        }
    }

    private Region readLocationFromConsole(String prompt) {
        while (true) {
            String location = readFromConsole(prompt);

            // Check if the location is present in the Region enum
            try {
                return Region.valueOf(location);
            } catch (IllegalArgumentException ex) {
                OutputDevice.write("Error: " + ex.getMessage() +
                        "\nAllowed values: (Romania, Germany, Italy, Spain, Sweden)");
            }
        }
    }

    private String readNameFromGui(JFrame frame, String input) {
        // Remove extra spaces and reduce them to a single space
        String name = input.replaceAll("\\s+", " ").trim();

        // Allow letters, spaces, and hyphens in the name
        if (name.matches("[a-zA-Z\\s-]+")) {
            return name;
        } else {
            JOptionPane.showMessageDialog(frame, "Invalid characters in the name. Please enter a valid name.", "Error", JOptionPane.ERROR_MESSAGE);
            // You can choose to handle this differently, like asking the user to input the name again
            return null;
        }
    }

    private void uploadDataToDatabase() {
        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish a connection to the database
            try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/employeedb", "angajat", "sefdesef")) {
                // Create a PreparedStatement for deleting all records from the table
                String deleteSql = "DELETE FROM `employeedb`.`employees`";

                try (PreparedStatement deleteStatement = connection.prepareStatement(deleteSql)) {
                    // Execute the delete statement to remove all records
                    deleteStatement.executeUpdate();
                }

                // Create a PreparedStatement for inserting data into the database
                String insertSql = "INSERT INTO `employeedb`.`employees` (name, age, role, is_married, region) VALUES (?, ?, ?, ?, ?)";

                try (PreparedStatement insertStatement = connection.prepareStatement(insertSql)) {
                    // Iterate through the table model and insert each employee into the database
                    for (int i = 0; i < tableModel.getRowCount(); i++) {
                        String name = (String) tableModel.getValueAt(i, 1);
                        int age = (int) tableModel.getValueAt(i, 2);
                        String role = (String) tableModel.getValueAt(i, 3);
                        boolean married = (boolean) tableModel.getValueAt(i, 4);
                        Region region = (Region) tableModel.getValueAt(i, 5);

                        // Set parameters for the prepared statement
                        insertStatement.setString(1, name);
                        insertStatement.setInt(2, age);
                        insertStatement.setString(3, role.toString());
                        insertStatement.setBoolean(4, married);
                        insertStatement.setString(5, region.toString());

                        // Execute the update
                        insertStatement.executeUpdate();
                    }

                    JOptionPane.showMessageDialog(null, "Data uploaded to the database successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error uploading data to the database.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean showLoginDialog() {
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        Object[] message = {
                "Username:", usernameField,
                "Password:", passwordField
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Login", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            String username = usernameField.getText();
            char[] passwordChars = passwordField.getPassword();
            String password = new String(passwordChars);

            // Check the login credentials
            if (validateLogin(username, password)) {
                currentUser = username;
                return true; // Login successful
            } else {
                JOptionPane.showMessageDialog(null, "Invalid username or password", "Login Failed", JOptionPane.ERROR_MESSAGE);
                return false; // Login failed
            }
        } else {
            return false; // User clicked Cancel
        }
    }

    private boolean validateLogin(String username, String password) {
        // Check the login credentials
        if ((username.equals("angajat") && password.equals("sefdesef"))) {
            currentUser = username;
            return true; // Valid credentials
        } else if (username.equals("averageman") && password.equals("simple")) {
            currentUser = username;

            // Check user permissions here
            if (hasDeletePermissions(username)) {
                return true; // Valid credentials
            } else {
                // Display a message for users with insufficient permissions
                JOptionPane.showMessageDialog(null, "User 'averageman' does not have delete permissions.", "Permission Denied", JOptionPane.ERROR_MESSAGE);
                return false; // Invalid permissions
            }
        }
        return false;
    }
    public class User {
        private String username;
        private Role role; // Add a field to store the user's role

        // Constructor, getters, setters...

        public boolean hasPermission(Role requiredPermission) {
            // Logic to check if the user has the required permission
            return role == requiredPermission;
        }
    }
    private boolean hasDeletePermissions(String username) {
        // Implement logic to check if the user has delete permissions
        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish a connection to the database
            try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/employeedb", "your_username", "your_password")) {
                // Create a PreparedStatement for the query
                String query = "SELECT delete_permission FROM user_permissions WHERE username = ?";
                try (PreparedStatement statement = connection.prepareStatement(query)) {
                    statement.setString(1, username);

                    // Execute the query
                    try (ResultSet resultSet = statement.executeQuery()) {
                        if (resultSet.next()) {
                            // Check the value of delete_permission in the result set
                            return resultSet.getBoolean("delete_permission");
                        }
                    }
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        // Return false by default (in case of errors or if the user is not found)
        return false;
    }
}

//todo junit
//mysql workbanch (mysql separat)
//pass J8J0*^sas