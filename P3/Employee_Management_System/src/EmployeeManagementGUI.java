import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;
import javax.swing.table.DefaultTableModel;

public class EmployeeManagementGUI {
    private ManagementSystem system;
    private DefaultTableModel tableModel;
    private boolean isDevEnabled;

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
                JComboBox<Role> functionCombo = new JComboBox<>(Role.values()); // Use JComboBox<Role> for Function
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

                int result = JOptionPane.showConfirmDialog(frame, panel, "Promote Employee",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                if (result == JOptionPane.OK_OPTION) {
                    String idText = idField.getText();
                    String ageText = ageField.getText();

                    if (idText.isEmpty() || ageText.isEmpty()) {
                        JOptionPane.showMessageDialog(frame, "ID and Age must not be empty!", "Error", JOptionPane.ERROR_MESSAGE);
                        return; // Exit the method early
                    }

                    int id = Integer.parseInt(idText);
                    String name = nameField.getText();
                    int age = Integer.parseInt(ageText);
                    Role function = (Role) functionCombo.getSelectedItem();
                    boolean isMarried = maritalStatusCombo.getSelectedItem().equals("Married");
                    Region region = (Region) regionCombo.getSelectedItem();

                    // Create a new employee and add it to the system
                    Employee newEmployee = new Employee(id, name, age, function, isMarried, region);
                    system.addEmployee(newEmployee);

                    // Add the new employee to the table
                    tableModel.addRow(new Object[]{id, name, age, function, isMarried, region});
                }
            }
        });

        // Clone the "Add Employee" button to create the "Edit Employee" button
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
                        JTextField functionField = new JTextField(selectedEmployee.getFunction());
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
                        panel.add(functionField);
                        panel.add(new JLabel("Marital Status:"));
                        panel.add(maritalStatusCombo);
                        panel.add(new JLabel("Region:"));
                        panel.add(regionCombo);

                        int result = JOptionPane.showConfirmDialog(frame, panel, "Edit Employee",
                                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                        if (result == JOptionPane.OK_OPTION) {
                            // Update the selected employee's details
                            selectedEmployee.setId(Integer.parseInt(idField.getText()));
                            selectedEmployee.setName(nameField.getText());
                            selectedEmployee.setAge(Integer.parseInt(ageField.getText()));
                            selectedEmployee.setFunction(functionField.getText());
                            selectedEmployee.setMarried(maritalStatusCombo.getSelectedItem().equals("Married"));
                            selectedEmployee.setRegion((Region) regionCombo.getSelectedItem());

                            // Update the table
                            tableModel.setValueAt(selectedEmployee.getId(), selectedRow, 0);
                            tableModel.setValueAt(selectedEmployee.getName(), selectedRow, 1);
                            tableModel.setValueAt(selectedEmployee.getAge(), selectedRow, 2);
                            tableModel.setValueAt(selectedEmployee.getFunction(), selectedRow, 3);
                            tableModel.setValueAt(selectedEmployee.isMarried(), selectedRow, 4);
                            tableModel.setValueAt(selectedEmployee.getRegion(), selectedRow, 5);
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

                    // Notify that the employee has been read successfully
                    System.out.println("Employee read successfully");

                    // Disable the button after reading the employee
                    readFromConsoleButton.setEnabled(false);
                } catch (NumberFormatException ex) {
                    System.out.println("Error: Invalid input. Please enter a valid number.");
                } catch (IllegalArgumentException ex) {
                    System.out.println("Error: Invalid input. Please enter a valid value.");
                }
            }
        });


        frame.add(tableScrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addEmployeeButton);
        buttonPanel.add(editEmployeeButton); // Add the "Edit Employee" button
        buttonPanel.add(removeEmployeeButton);
        buttonPanel.add(promoteEmployeeButton);
        buttonPanel.add(readFromConsoleButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }
    private String readFromConsole(String prompt) {
        System.out.print(prompt);
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        System.out.println("User input: " + input); // Print the input to the output console
        return input;
    }

    private boolean readYesNoFromConsole(String prompt) {
        while (true) {
            String response = readFromConsole(prompt).toLowerCase();

            if (response.equals("y") || response.equals("n")) {
                return response.equals("Yes");
            } else {
                System.out.println("Error: Invalid input. Please enter 'y' or 'n'.");
            }
        }
    }

    private int readIntFromConsole(String prompt) {
        while (true) {
            try {
                return Integer.parseInt(readFromConsole(prompt));
            } catch (NumberFormatException ex) {
                System.out.println("Error: Invalid input. Please enter a valid number.");
            }
        }
    }

    private String readNameFromConsole(String prompt) {
        while (true) {
            String name = readFromConsole(prompt);
            if (name.matches("[a-zA-Z]+")) {
                return name;
            } else {
                System.out.println("Error: Why would you have numbers in your name? =)");
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
                    System.out.println("Error: Invalid input. Please enter an age between 1 and 200.");
                }
            } catch (NumberFormatException ex) {
                System.out.println("Error: Invalid input. Please enter a valid number for the age.");
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
                System.out.println("Error: " + ex.getMessage() +
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
                System.out.println("Error: " + ex.getMessage() +
                        "\nAllowed values: (Romania, Germany, Italy, Spain, Sweden)");
            }
        }
    }

}