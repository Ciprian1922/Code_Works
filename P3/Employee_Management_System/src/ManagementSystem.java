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

    public Employee getEmployeeById(int id) {
        for (Employee employee : employees) {
            if (employee.getId() == id) {
                return employee;
            }
        }
        return null; // If no employee with the given ID is found
    }
}
