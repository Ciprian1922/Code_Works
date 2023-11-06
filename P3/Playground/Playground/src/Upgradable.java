public class Employee implements Upgradable {
    // Other fields and methods

    @Override
    public boolean isUpgradable() {
        // Implement your logic to determine if the employee can be upgraded
        // Return true if upgradable, false otherwise
        // Example logic: return age >= 30;
    }

    @Override
    public void upgrade() {
        // Implement the logic for upgrading the employee
        // Example: increase salary, change role, etc.
    }
}

