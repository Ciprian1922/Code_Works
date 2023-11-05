public class Employee {
    private int id;
    private String name;
    private int age;
    private String function;
    private boolean married;
    private Region region; // Added region field

    public Employee(int id, String name, int age, String function, boolean married, Region region) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.function = function;
        this.married = married;
        this.region = region; // Initialize the region
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getFunction() {
        return function;
    }

    public boolean isMarried() {
        return married;
    }

    // Getter and setter methods for the region
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
