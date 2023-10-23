import java.util.ArrayList;
import java.util.List;

public class InputDevice {
    public static final int ceva = 10;
    public final int altcevaceva = 11;
    //Define window size
    public static final int width = 600;
    public static final int height = 400;
    // Define a list of default employees
    public static List<Employee> defaultEmployees = new ArrayList<>();

    static {
        defaultEmployees.add(new Employee(1, "John", 30, "Manager", true));
        defaultEmployees.add(new Employee(2, "Alice", 25, "Developer", false));
        defaultEmployees.add(new Employee(3, "Berna", 21, "Slaav", false));
        defaultEmployees.add(new Employee(4, "Andrei", 28, "Designer", true));
        defaultEmployees.add(new Employee(5, "Elena", 23, "Marketing", false));
        defaultEmployees.add(new Employee(6, "Mihai", 31, "HR", true));
    }

}
