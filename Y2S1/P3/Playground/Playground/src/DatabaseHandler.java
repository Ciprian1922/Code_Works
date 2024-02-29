import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class DatabaseHandler {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/employeedb";
    private static final String DB_USER_AVERAGEMAN = "averageman";
    private static final String DB_PASSWORD_AVERAGEMAN = "simple";
    private static final String DB_USER_ANGAJAT = "angajat";
    private static final String DB_PASSWORD_ANGAJAT = "sefdesef";

    public static DefaultTableModel fetchDataFromDatabase(String orderBy, String username, String password) {
        DefaultTableModel model = new DefaultTableModel(new Object[]{"ID", "Name", "Age", "Role", "Married", "Region"}, 0);

        // Set credentials for averageman
        if ("averageman".equals(username)) {
            username = DB_USER_AVERAGEMAN;
            password = DB_PASSWORD_AVERAGEMAN;
        }

        try (Connection connection = DriverManager.getConnection(DB_URL, username, password)) {
            String query = buildQuery(orderBy, username, password);

            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {

                while (resultSet.next()) {
                    String region = resultSet.getString("Region");

                    // Debugging statement to print the user and region for each row
                    System.out.println("User: " + username + ", Region: " + region);

                    // Filter rows based on the user
                    if ("averageman".equals(username) && !"Romania".equals(region)) {
                        continue; // Skip this row if averageman and not from Romania
                    }

                    Object[] rowData = {
                            resultSet.getInt("ID"),
                            resultSet.getString("Name"),
                            resultSet.getInt("Age"),
                            resultSet.getString("Role"),
                            resultSet.getBoolean("is_married"),
                            region
                    };

                    model.addRow(rowData);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return model;
    }



    private static String getUsername(String username) {
        switch (username) {
            case "angajat":
                return DB_USER_ANGAJAT;
            case "averageman":
                return DB_USER_AVERAGEMAN;
            default:
                System.out.println("Unknown user: " + username); // Debugging statement
                return ""; // Default case, empty string (you can modify this based on your default behavior)
        }
    }


    private static String getPassword(String password) {
        switch (password) {
            case "sefdesef":
                return DB_PASSWORD_ANGAJAT;
            default:
                return DB_PASSWORD_AVERAGEMAN; // Default password
        }
    }

    private static String buildQuery(String orderBy, String username, String password) {
        if ("angajat".equals(username)) {
            // If the user is 'angajat,' fetch all employees without filtering by region
            return "SELECT * FROM employees ORDER BY " + orderBy;
        } else if ("averageman".equals(username)) {
            // If the user is 'averageman,' fetch only employees from Romania
            return "SELECT * FROM employees WHERE Region = 'Romania' ORDER BY " + orderBy;
        } else {
            // Default case, fetch all employees without filtering by region
            return "SELECT * FROM employees ORDER BY " + orderBy;
        }
    }
}
