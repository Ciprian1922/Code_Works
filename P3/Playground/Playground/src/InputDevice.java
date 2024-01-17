import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class InputDevice {
    public static final int ceva = 10;
    public final int altcevaceva = 11;

    public static final int width = 1100;
    public static final int height = 600;

    public static List<Employee> defaultEmployees = new ArrayList<>();

    static {
        defaultEmployees.add(new Employee(1, "John", 30, Role.Ceo, true, Region.Romania));
        defaultEmployees.add(new Employee(2, "Alice", 25, Role.Lead, false, Region.Germany));
        defaultEmployees.add(new Employee(3, "Berna", 21, Role.Intern, false, Region.Italy));
        defaultEmployees.add(new Employee(4, "Andrei", 28, Role.Director, true, Region.Spain));
        defaultEmployees.add(new Employee(5, "Elena", 23, Role.Associate, false, Region.Sweden));
        defaultEmployees.add(new Employee(6, "Mihai", 31, Role.Team_Lead, true, Region.Romania));
        defaultEmployees.add(new Employee(7, "Sarah", 27, Role.Intern, false, Region.Italy));
        defaultEmployees.add(new Employee(8, "Michael", 26, Role.Junior, true, Region.Germany));
        defaultEmployees.add(new Employee(9, "Laura", 22, Role.Senior, false, Region.Italy));
        defaultEmployees.add(new Employee(10, "David", 29, Role.Lead, true, Region.Spain));
        defaultEmployees.add(new Employee(11, "Emily", 24, Role.Intermediate, false, Region.Sweden));
        defaultEmployees.add(new Employee(12, "Robert", 32, Role.Team_Lead, true, Region.Romania));
        defaultEmployees.add(new Employee(13, "Sophia", 29, Role.Associate, true, Region.Italy));
        defaultEmployees.add(new Employee(14, "Daniel", 26, Role.Director, false, Region.Germany));
    }


    public static List<Employee> loadEmployeesFromFile() {
        List<Employee> employees = new ArrayList<>();
        String fileName = "src/emp.txt";

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 6) {
                    int id = Integer.parseInt(data[0]);
                    String name = data[1];
                    int age = Integer.parseInt(data[2]);
                    Role function = Role.valueOf(data[3]);
                    boolean isMarried = Boolean.parseBoolean(data[4]);
                    Region region;

                    if ("null".equalsIgnoreCase(data[5])) {
                        // Handle the case where the region is specified as "null"
                        region = Region.Romania; // Replace with an appropriate default region
                    } else {
                        region = Region.valueOf(data[5]);
                    }

                    employees.add(new Employee(id, name, age, function, isMarried, region));
                }
            }
        } catch (FileNotFoundException e) {
            //file not found, create the default file with default employees
            createDefaultFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return employees;
    }

    private static void createDefaultFile() {
        String fileName = "src/emp.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            defaultEmployees.sort(Comparator.comparingInt(Employee::getId));
            for (Employee employee : defaultEmployees) {
                writer.write(
                        employee.getId() + "," +
                                employee.getName() + "," +
                                employee.getAge() + "," +
                                employee.getFunction() + "," +
                                employee.isMarried() + "," +
                                employee.getRegion() + "\n"
                );
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveEmployeesToFile(List<Employee> employees, boolean wasLoaded) {
        if (wasLoaded) {
            String fileName = "src/emp.txt";
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
                employees.sort(Comparator.comparingInt(Employee::getId));
                for (Employee employee : employees) {
                    writer.write(
                            employee.getId() + "," +
                                    employee.getName() + "," +
                                    employee.getAge() + "," +
                                    employee.getFunction() + "," +
                                    employee.isMarried() + "," +
                                    employee.getRegion() + "\n"
                    );
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
