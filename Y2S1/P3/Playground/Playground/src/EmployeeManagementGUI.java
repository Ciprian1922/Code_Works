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
    private boolean isDbEnabled;
    private boolean isLoggedIn = false;
    private String currentUser = null;
    public boolean validateEmployeeInput(int id, String name, int age, Role function, boolean isMarried, Region region) {
        if (id <= 0) {
            JOptionPane.showMessageDialog(null, "Invalid ID. Please enter a positive integer.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (name.isEmpty()) {
            System.out.println("Name must not be empty.");
            return false;
        }

        if (age <= 0) {
            System.out.println("Invalid age. Please enter a positive integer.");
            return false;
        }
        return true;
    }

    public EmployeeManagementGUI(ManagementSystem system, boolean isDevEnabled, boolean isLoadEnabled, boolean isDbEnabled) {
        this.system = system;
        this.tableModel = new DefaultTableModel(new Object[]{"ID", "Name", "Age", "Function", "Married", "Region"}, 0);
        for (Employee employee : system.getEmployees()) {
            tableModel.addRow(new Object[]{employee.getId(), employee.getName(), employee.getAge(), employee.getFunction(), employee.isMarried(), employee.getRegion()});
        }
        this.isDevEnabled = isDevEnabled;
        this.isDbEnabled = isDbEnabled;
    }

    public Role getNextRole(Role currentRole) {
        Role[] roles = Role.values();
        for (int i = 0; i < roles.length - 1; i++) {
            if (roles[i] == currentRole) {
                return roles[i + 1];
            }
        }
        return null;
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

                if (result == JOptionPane.OK_OPTION) {
                    String idText = idField.getText();
                    String ageText = ageField.getText();

                    if (idText.isEmpty() || ageText.isEmpty()) {
                        JOptionPane.showMessageDialog(frame, "ID and Age must not be empty!", "Error", JOptionPane.ERROR_MESSAGE);
                        return; 
                    }

                    boolean isValidInput = true;

                    try {
                        int id = Integer.parseInt(idText);
                        if (system.employeeExists(id)) {
                            JOptionPane.showMessageDialog(frame, "An employee with the same ID already exists.", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        String name = readNameFromGui(frame, nameField.getText());
                        int age = Integer.parseInt(ageText);
                        Role function = (Role) functionCombo.getSelectedItem();
                        boolean isMarried = maritalStatusCombo.getSelectedItem().equals("Married");
                        Region region = (Region) regionCombo.getSelectedItem();

                        isValidInput = validateEmployeeInput(id, name, age, function, isMarried, region);

                        if (isValidInput) {
                            Employee newEmployee = new Employee(id, name, age, function, isMarried, region);
                            system.addEmployee(newEmployee);
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
                    Employee selectedEmployee = null;
                    for (Employee employee : system.getEmployees()) {
                        if (employee.getId() == id) {
                            selectedEmployee = employee;
                            break;
                        }
                    }

                    if (selectedEmployee != null) {
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
                                return;
                            }

                            boolean isValidInput = true;

                            try {
                                int editedId = Integer.parseInt(idText);
                                String editedName = readNameFromGui(frame, nameField.getText());
                                int editedAge = Integer.parseInt(ageText);
                                Role editedFunction = (Role) functionCombo.getSelectedItem();
                                boolean isMarried = maritalStatusCombo.getSelectedItem().equals("Married");
                                Region editedRegion = (Region) regionCombo.getSelectedItem();

                                if (editedName == null) {
                                    isValidInput = false;
                                }

                                if (isValidInput) {
                                    selectedEmployee.setId(editedId);
                                    selectedEmployee.setName(editedName);
                                    selectedEmployee.setAge(editedAge);
                                    selectedEmployee.setFunction(String.valueOf(editedFunction));
                                    selectedEmployee.setMarried(isMarried);
                                    selectedEmployee.setRegion(editedRegion);
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

        //"Promote Employee" button and its action listener
        JButton promoteEmployeeButton = new JButton("Promote Employee");
        promoteEmployeeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = employeeTable.getSelectedRow();

                if (selectedRow != -1) {
                    int id = (int) tableModel.getValueAt(selectedRow, 0);
                    Employee selectedEmployee = system.getEmployeeById(id);
                    if (selectedEmployee != null) {
                        //is upgradable
                        if (selectedEmployee.isUpgradable()) {
                            //upgrade
                            selectedEmployee.upgrade();
                            //update the table to reflect the change
                            tableModel.setValueAt(selectedEmployee.getFunction(), selectedRow, 3); //assuming the column index for function is 3
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
                    int id = readIntFromConsole("Provide the ID of the employee: ");
                    String name = readNameFromConsole("Provide the name of the employee: ");
                    int age = readAgeFromConsole("Provide the age of the employee: ");
                    Role function = Role.valueOf(readRoleFromConsole("Provide the Function of the employee: (Intern, Junior, Associate, Intermediate, Senior, Lead, Team_Lead, Director, Ceo) "));
                    boolean isMarried = readYesNoFromConsole("Is the employee married? (y/n): ");
                    Region region = readLocationFromConsole("Provide the Region of the employee: (Romania, Germany, Italy, Spain, Sweden)");
                    Employee newEmployee = new Employee(id, name, age, function, isMarried, region);
                    system.addEmployee(newEmployee);
                    tableModel.addRow(new Object[]{id, name, age, function, isMarried, region});
                    //SQL THING THAT ADDS THE EMPLOYEE
                    OutputDevice.write("Employee read successfully");
                    readFromConsoleButton.setEnabled(true);

                } catch (NumberFormatException ex) {
                    OutputDevice.write("Error: Invalid input. Please enter a valid number.");
                } catch (IllegalArgumentException ex) {
                    OutputDevice.write("Error: Invalid input. Please enter a valid value.");
                }
            }
        });

        //"Battle" button and its action listener
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
                if (result == JOptionPane.OK_OPTION) {
                    Employee employee1 = (Employee) employee1Combo.getSelectedItem();
                    Employee employee2 = (Employee) employee2Combo.getSelectedItem();

                    //determine the winner and loser (randomly for simplicity)
                    Employee winner = (Math.random() < 0.5) ? employee1 : employee2;
                    Employee loser = (winner == employee1) ? employee2 : employee1;

                    //display the winner in a new popup
                    JOptionPane.showMessageDialog(frame, winner.getName() + " won the battle!", "Battle Result", JOptionPane.INFORMATION_MESSAGE);

                    // remove the loser employee from the system and the table
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
                    //if not logged in, show the login dialog
                    if (showLoginDialog()) {
                        //if login is successful, set the flag to true
                        isLoggedIn = true;
                    }
                }

                if (isLoggedIn) {
                    //if logged in, proceed with the data upload
                    uploadDataToDatabase();
                }
            }
        });

        //"Stats" button and its action listener
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
                //reset the isLoggedIn variable and set currentUser to null
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

                //check if the ID already exists
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

            //remove extra spaces and reduce them to a single space
            name = name.replaceAll("\\s+", " ").trim();

            //allow letters, spaces, and hyphens in the name
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

            //check if the function is present in the Role enum
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

            //check if the location is present in the Region enum
            try {
                return Region.valueOf(location);
            } catch (IllegalArgumentException ex) {
                OutputDevice.write("Error: " + ex.getMessage() +
                        "\nAllowed values: (Romania, Germany, Italy, Spain, Sweden)");
            }
        }
    }

    private String readNameFromGui(JFrame frame, String input) {
        //remove extra spaces and reduce them to a single space
        String name = input.replaceAll("\\s+", " ").trim();

        //allow letters, spaces, and hyphens in the name
        if (name.matches("[a-zA-Z\\s-]+")) {
            return name;
        } else {
            JOptionPane.showMessageDialog(frame, "Invalid characters in the name. Please enter a valid name.", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    private void uploadDataToDatabase() {
        try {
            //load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            //connection to the database
            try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/employeedb", "angajat", "sefdesef")) {
                //deleting all records from the table
                String deleteSql = "DELETE FROM `employeedb`.`employees`";

                try (PreparedStatement deleteStatement = connection.prepareStatement(deleteSql)) {
                    //execute the delete statement to remove all records
                    deleteStatement.executeUpdate();
                }

                //inserting data into the database
                String insertSql = "INSERT INTO `employeedb`.`employees` (name, age, role, is_married, region) VALUES (?, ?, ?, ?, ?)";

                try (PreparedStatement insertStatement = connection.prepareStatement(insertSql)) {
                    //iterate through the table model and insert each employee into the database
                    for (int i = 0; i < tableModel.getRowCount(); i++) {
                        String name = (String) tableModel.getValueAt(i, 1);
                        int age = (int) tableModel.getValueAt(i, 2);
                        String role = tableModel.getValueAt(i, 3).toString(); //use toString() method
                        boolean married = (boolean) tableModel.getValueAt(i, 4);
                        Region region = (Region) tableModel.getValueAt(i, 5);
                        //set parameters for the prepared statement
                        insertStatement.setString(1, name);
                        insertStatement.setInt(2, age);
                        insertStatement.setString(3, role);
                        insertStatement.setBoolean(4, married);
                        insertStatement.setString(5, region.toString());
                        //execute the update
                        insertStatement.executeUpdate();
                    }


                    JOptionPane.showMessageDialog(null, "Data uploaded to the database successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error uploading data to the database.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        JButton loadDbButton = new JButton("Load from DB");
        loadDbButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (isDbEnabled) {
                    //load employees from the database
                    loadEmployeesFromDatabase();
                }
            }
        });
    }

    private void loadEmployeesFromDatabase() {
        String dbUrl = "jdbc:mysql://localhost:3306/employeedb";
        String dbUser;
        String dbPassword;
        if ("averageman".equals(currentUser)) {
            dbUser = "averageman";
            dbPassword = "simple";
        } else {
            dbUser = "angajat";
            dbPassword = "sefdesef";
        }

        DefaultTableModel tableModel = DatabaseHandler.fetchDataFromDatabase("Name", dbUser, dbPassword);
        tableModel.setRowCount(0);
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            String region = (String) tableModel.getValueAt(i, 5);

            // only add employees from Romania to the table model when the user is 'averageman'
            if ("averageman".equals(currentUser) && !"Romania".equals(region)) {
                continue;
            }
            tableModel.addRow(new Object[]{
                    tableModel.getValueAt(i, 0),
                    tableModel.getValueAt(i, 1),
                    tableModel.getValueAt(i, 2),
                    tableModel.getValueAt(i, 3),
                    tableModel.getValueAt(i, 4),
                    region
            });
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

            //check the login credentials
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

    private boolean validateLogin(String username, String password) {
        //check the login credentials
        if ("angajat".equals(username) && "sefdesef".equals(password)) {
            currentUser = username;
            return true; //valid credentials
        } else if ("averageman".equals(username) && "simple".equals(password)) {
            currentUser = username;
            return true; //valid credentials
        }

        //invalid credentials
        JOptionPane.showMessageDialog(null, "Invalid username or password", "Login Failed", JOptionPane.ERROR_MESSAGE);
        return false;
    }
    public class User {
        private String username;
        private Role role; 
        public boolean hasPermission(Role requiredPermission) {
            return role == requiredPermission;
        }
    }
    private boolean hasDeletePermissions(String username) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/employeedb", "averageman", "simple")) {
                String query = "SELECT delete_permission FROM user_permissions WHERE username = ?";
                try (PreparedStatement statement = connection.prepareStatement(query)) {
                    statement.setString(1, username);
                    try (ResultSet resultSet = statement.executeQuery()) {
                        if (resultSet.next()) {
                            return resultSet.getBoolean("delete_permission");
                        }
                    }
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}

//todo junit
//mysql workbanch (mysql separat)
//pass J8J0*^sas
