import java.util.ArrayList;
import java.util.List;

public class InputDevice {
    public static final int ceva = 10;
    public final int altcevaceva = 11;
    // Define a list of default employees
    public static List<Employee> defaultEmployees = new ArrayList<>();

    static {
        defaultEmployees.add(new Employee("John", 30, "Manager", true));
        defaultEmployees.add(new Employee("Alice", 25, "Developer", false));
        defaultEmployees.add(new Employee("Berna", 21, "Slaav", false));
        defaultEmployees.add(new Employee("Andrei", 28, "Designer", true));
        defaultEmployees.add(new Employee("Elena", 23, "Marketing", false));
        defaultEmployees.add(new Employee("Mihai", 31, "HR", true));
    }

}
