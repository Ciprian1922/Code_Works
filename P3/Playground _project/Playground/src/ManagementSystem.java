import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;
class ManagementSystem {
    private ArrayList<Employee> employees = new ArrayList<>();
    private static final Role[] rolesOrder = {Role.Intern, Role.Junior, Role.Associate, Role.Intermediate, Role.Senior, Role.Lead, Role.Team_Lead, Role.Director, Role.Ceo};

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
            OutputDevice.write(String.valueOf(employee));
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

    public boolean employeeExists(int id) {
        for (Employee employee : employees) {
            if (employee.getId() == id) {
                return true; // Employee with the given ID already exists
            }
        }
        return false; // No employee with the given ID found
    }
}
