import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class EmployeeListWindow {
    private JFrame frame;
    private JPanel cardPanel;
    private CardLayout cardLayout;
    private DefaultTableModel tableModel;
    private DefaultTableModel alphabeticalTableModel;
    private DefaultTableModel roleStatsTableModel;

    // Database connection details
    private static final String DB_URL = "jdbc:mysql://localhost:3306/employeedb";
    private static final String DB_USER = "angajat";
    private static final String DB_PASSWORD = "sefdesef";

    public EmployeeListWindow() {
        initialize();
        fetchDataFromDatabase(tableModel, "ID");
    }

    private void initialize() {
        frame = new JFrame("Employee List");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cardPanel = new JPanel();
        cardLayout = new CardLayout();
        cardPanel.setLayout(cardLayout);

        // Original Scene
        createOriginalScene();

        // Alphabetical Scene
        createAlphabeticalScene();

        // Role Statistics Scene
        createRoleStatsScene();

        frame.add(cardPanel);
        frame.setVisible(true);
    }

    private void fetchDataFromDatabase(DefaultTableModel model, String orderBy) {
        // Create a new model if it is null
        if (model == null) {
            model = new DefaultTableModel(new Object[]{"ID", "Name", "Age", "Role", "Married", "Region"}, 0);
        } else {
            // Clear the existing data in the table model
            clearTableModel(model);
        }

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT * FROM employees ORDER BY " + orderBy;
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {

                while (resultSet.next()) {
                    Object[] rowData = {
                            resultSet.getInt("ID"),
                            resultSet.getString("Name"),
                            resultSet.getInt("Age"),
                            resultSet.getString("Role"),
                            resultSet.getBoolean("is_married"),
                            resultSet.getString("Region")
                    };
                    model.addRow(rowData);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void clearTableModel(DefaultTableModel model) {
        while (model.getRowCount() > 0) {
            model.removeRow(0);
        }
    }

    private void createOriginalScene() {
        JPanel originalPanel = new JPanel(new BorderLayout());

        tableModel = new DefaultTableModel(new Object[]{"ID", "Name", "Age", "Role", "Married", "Region"}, 0);
        JTable employeeTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(employeeTable);

        originalPanel.add(tableScrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton switchAlphabeticalButton = new JButton("Switch to Alphabetical Scene");
        switchAlphabeticalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fetchDataFromDatabase(alphabeticalTableModel, "Name");
                cardLayout.show(cardPanel, "Alphabetical");
            }
        });
        JButton switchRoleStatsButton = new JButton("Switch to Role Statistics Scene");
        switchRoleStatsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fetchDataForRoleStats();
                cardLayout.show(cardPanel, "RoleStats");
            }
        });
        buttonPanel.add(switchAlphabeticalButton);
        buttonPanel.add(switchRoleStatsButton);

        originalPanel.add(buttonPanel, BorderLayout.SOUTH);

        cardPanel.add(originalPanel, "Original");
    }

    private void createAlphabeticalScene() {
        JPanel alphabeticalPanel = new JPanel(new BorderLayout());

        alphabeticalTableModel = new DefaultTableModel(new Object[]{"ID", "Name", "Age", "Role", "Married", "Region"}, 0);
        JTable alphabeticalTable = new JTable(alphabeticalTableModel);
        JScrollPane alphabeticalScrollPane = new JScrollPane(alphabeticalTable);

        alphabeticalPanel.add(alphabeticalScrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton switchOriginalButton = new JButton("Switch to Original Scene");
        switchOriginalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fetchDataFromDatabase(tableModel, "ID");
                cardLayout.show(cardPanel, "Original");
            }
        });
        JButton switchRoleStatsButton = new JButton("Switch to Role Statistics Scene");
        switchRoleStatsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fetchDataForRoleStats();
                cardLayout.show(cardPanel, "RoleStats");
            }
        });
        buttonPanel.add(switchOriginalButton);
        buttonPanel.add(switchRoleStatsButton);

        alphabeticalPanel.add(buttonPanel, BorderLayout.SOUTH);

        cardPanel.add(alphabeticalPanel, "Alphabetical");
    }

    private void createRoleStatsScene() {
        JPanel roleStatsPanel = new JPanel(new BorderLayout());

        roleStatsTableModel = new DefaultTableModel(new Object[]{"Role", "Count", "Percentage"}, 0);
        JTable roleStatsTable = new JTable(roleStatsTableModel);
        JScrollPane roleStatsScrollPane = new JScrollPane(roleStatsTable);

        roleStatsPanel.add(roleStatsScrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton switchOriginalButton = new JButton("Switch to Original Scene");
        switchOriginalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fetchDataFromDatabase(tableModel, "ID");
                cardLayout.show(cardPanel, "Original");
            }
        });
        JButton switchAlphabeticalButton = new JButton("Switch to Alphabetical Scene");
        switchAlphabeticalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fetchDataFromDatabase(alphabeticalTableModel, "Name");
                cardLayout.show(cardPanel, "Alphabetical");
            }
        });
        buttonPanel.add(switchOriginalButton);
        buttonPanel.add(switchAlphabeticalButton);

        roleStatsPanel.add(buttonPanel, BorderLayout.SOUTH);

        cardPanel.add(roleStatsPanel, "RoleStats");
    }

    private void fetchDataForRoleStats() {
        // Clear the existing data in the roleStatsTableModel
        clearTableModel(roleStatsTableModel);

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT Role, COUNT(*) AS Count, (COUNT(*) / (SELECT COUNT(*) FROM employees)) * 100 AS Percentage " +
                    "FROM employees GROUP BY Role";
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {

                while (resultSet.next()) {
                    Object[] rowData = {
                            resultSet.getString("Role"),
                            resultSet.getInt("Count"),
                            resultSet.getDouble("Percentage")
                    };
                    roleStatsTableModel.addRow(rowData);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new EmployeeListWindow();
            }
        });
    }
}
