import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;

public class EmployeeManagementGUI {
    private ManagementSystem system;
    private DefaultTableModel tableModel;
    private boolean isDevEnabled;

    public EmployeeManagementGUI(ManagementSystem system, boolean isDevEnabled) {
        this.system = system;
        this.tableModel = new DefaultTableModel(new Object[]{"Name", "Age", "Function", "Married"}, 0);
        // Populate the table model with the employees from the ManagementSystem
        for (Employee employee : system.getEmployees()) {
            tableModel.addRow(new Object[]{employee.getName(), employee.getAge(), employee.getFunction(), employee.isMarried()});
        }
        this.isDevEnabled = isDevEnabled;
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
                JTextField nameField = new JTextField();
                JTextField ageField = new JTextField();
                JTextField functionField = new JTextField();
                String[] maritalStatusOptions = {"Married", "Single"};
                JComboBox<String> maritalStatusCombo = new JComboBox<>(maritalStatusOptions);

                JPanel panel = new JPanel(new GridLayout(5, 2));
                panel.add(new JLabel("Name:"));
                panel.add(nameField);
                panel.add(new JLabel("Age:"));
                panel.add(ageField);
                panel.add(new JLabel("Function:"));
                panel.add(functionField);
                panel.add(new JLabel("Marital Status:"));
                panel.add(maritalStatusCombo);

                int result = JOptionPane.showConfirmDialog(frame, panel, "Add Employee",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                if (result == JOptionPane.OK_OPTION) {
                    String name = nameField.getText();
                    int age = Integer.parseInt(ageField.getText());
                    String function = functionField.getText();
                    boolean isMarried = maritalStatusCombo.getSelectedItem().equals("Married");

                    // Create a new employee and add it to the system
                    Employee newEmployee = new Employee(name, age, function, isMarried);
                    system.addEmployee(newEmployee);

                    // Add the new employee to the table
                    tableModel.addRow(new Object[]{name, age, function, isMarried});
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
                    String name = (String) tableModel.getValueAt(selectedRow, 0);
                    for (Employee employee : system.getEmployees()) {
                        if (employee.getName().equals(name)) {
                            system.removeEmployee(employee);
                            tableModel.removeRow(selectedRow);
                            break;
                        }
                    }
                }
            }
        });


        frame.add(tableScrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addEmployeeButton);
        buttonPanel.add(removeEmployeeButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }
}
