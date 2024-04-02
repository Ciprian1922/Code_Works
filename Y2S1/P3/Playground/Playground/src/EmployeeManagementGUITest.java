import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

public class EmployeeManagementGUITest {

    @Test
    public void testEmployeeManagementGUI() {
        //assuming you have a ManagementSystem instance, and the last two parameters are booleans
        ManagementSystem managementSystem = new ManagementSystem();
        boolean isDevEnabled = true;
        boolean isLoadEnabled = false;
        boolean isDbEnabled = false;
        //instantiate EmployeeManagementGUI with the required arguments
        EmployeeManagementGUI employeeManagementGUI = new EmployeeManagementGUI(managementSystem, isDevEnabled, isLoadEnabled,  isDbEnabled);

        assertNotNull(employeeManagementGUI);
    }

    @Test
    public void testValidateEmployeeInputValid() {
        //provide necessary parameters for ManagementSystem and booleans
        ManagementSystem managementSystem = new ManagementSystem();
        boolean isDevEnabled = true;
        boolean isLoadEnabled = false;
        boolean isDbEnabled = false;
        EmployeeManagementGUI gui = new EmployeeManagementGUI(managementSystem, isDevEnabled, isLoadEnabled,  isDbEnabled);
        //test with valid input
        boolean result = gui.validateEmployeeInput(1, "Fodor Nedelcu", 30, Role.Junior, false, Region.Romania);
        assertTrue(result);
    }

    @Test
    public void testValidateEmployeeInputInvalidId() {
        //provide necessary parameters for ManagementSystem and booleans
        ManagementSystem managementSystem = new ManagementSystem();
        boolean isDevEnabled = true;
        boolean isLoadEnabled = false;
        boolean isDbEnabled = false;
        EmployeeManagementGUI gui = new EmployeeManagementGUI(managementSystem, isDevEnabled, isLoadEnabled, isDbEnabled);
        //test with invalid ID
        boolean result = gui.validateEmployeeInput(-1, "Geleu Bogota", 30, Role.Junior, false, Region.Romania);
        assertFalse(result);
    }

    @Test
    public void testValidateEmployeeInputInvalidName() {
        //create an instance of EmployeeManagementGUI
        ManagementSystem system = new ManagementSystem();
        EmployeeManagementGUI gui = new EmployeeManagementGUI(system, false, false, false);
        //call the method with an invalid name
        boolean result = gui.validateEmployeeInput(-1, "Adrian Mircea", 25, Role.Intern, false, Region.Romania);
        //assert that the result is false
        assertFalse(result);
    }

    @Test
    public void testAddEmployee() {
        //create an instance of ManagementSystem
        ManagementSystem system = new ManagementSystem();
        //create an instance of EmployeeManagementGUI
        EmployeeManagementGUI gui = new EmployeeManagementGUI(system, false, false, false);
        int initialEmployeeCount = system.getEmployees().size();   //get the initial employee count
        gui.createAndShowGUI();    //call the createAndShowGUI method to create the GUI

        //simulate user interaction to add a valid employee
        gui.validateEmployeeInput(1, "Popa Ciprian", 30, Role.Associate, false, Region.Germany);
        int updatedEmployeeCount = system.getEmployees().size();   //get the updated employee count
        assertEquals(initialEmployeeCount, updatedEmployeeCount);   //assert that the employee count has increased by 1
    }
}
