public class Employee implements Upgradable {
    private int id;
    private String name;
    private int age;
    private String function;
    private boolean married;
    private Region region;

    public Employee(int id, String name, int age, Role function, boolean married, Region region) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.function = String.valueOf(function);
        this.married = married;
        this.region = region;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public boolean isMarried() {
        return married;
    }

    public boolean isUpgradable() {
        return !this.getFunction().equalsIgnoreCase("Ceo");
    }

    public void upgrade() {
        String currentRole = this.getFunction();
        Role role = Role.valueOf(currentRole);

        // Get the next role
        Role nextRole = getNextRole(role);

        if (nextRole != null) {
            this.setFunction(nextRole.toString());
        }
    }

    public static Role getNextRole(Role currentRole) {
        Role[] roles = Role.values();
        for (int i = 0; i < roles.length - 1; i++) {
            if (roles[i] == currentRole) {
                return roles[i + 1];
            }
        }
        return null; // If the current role is the highest, return null
    }

    public void setMarried(boolean married) {
        this.married = married;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Age: " + age + ", Function: " + function + ", Married: " + married + ", Region: " + region;
    }
}
