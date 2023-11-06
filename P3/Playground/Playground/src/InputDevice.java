import java.util.ArrayList;
import java.util.List;

public class InputDevice {
    public static final int ceva = 10;
    public final int altcevaceva = 11;
    // Define window size
    public static final int width = 800;
    public static final int height = 600;
    // Define a list of default employees with regions
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
}
