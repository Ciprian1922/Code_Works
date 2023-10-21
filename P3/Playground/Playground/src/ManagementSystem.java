import java.util.ArrayList;
import java.util.List;

class ManagementSystem {
    private ArrayList<Employee> employees = new ArrayList<>();

    public void addEmployee(Employee employee) {
        employees.add(employee);
    }

    public void removeEmployee(Employee employee) {
        employees.remove(employee);
    }

    public ArrayList<Employee> getEmployees() {
        return employees;
    }

    public void describeEmployees() {
        for (Employee employee : employees) {
            System.out.println(employee);
        }
    }
    public void addEmployees(List<Employee> employeesToAdd) {
        employees.addAll(employeesToAdd);
    }
}
