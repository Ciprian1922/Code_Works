class Employee {
    private String name;
    private int age;
    private String function;
    private boolean married;

    public Employee(String name, int age, String function, boolean married) {
        this.name = name;
        this.age = age;
        this.function = function;
        this.married = married;
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

    @Override
    public String toString() {
        return "Name: " + name + ", Age: " + age + ", Function: " + function + ", Married: " + married;
    }
}
