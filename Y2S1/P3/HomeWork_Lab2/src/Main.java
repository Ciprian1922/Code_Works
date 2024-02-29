public class Main {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Please provide an argument: 'function1' or 'function2'");
            return;
        }

        if (args[0].equals("function1")) {
            function1();
        } else if (args[0].equals("function2")) {
            function2();
        } else {
            System.out.println("Invalid argument, please use 'function1' or 'function2' inorder to run the program.");
        }
    }

    public static void function1() {
        A a = new A("This is property A");
        C[] cArray = { new C(), new C(), new C() };
        B b = new B(a, cArray);

        System.out.println("Function 1 - Displaying class A and class B properties:");
        System.out.println("A.propertyA: " + a.getPropertyA());
        System.out.println("B.memberA.propertyA: " + b.getMemberA().getPropertyA());
        System.out.println("B.memberCArray length: " + b.getMemberCArray().length);
    }

    public static void function2() {
        // Implement your second function's logic here
        System.out.println("Function 2 - Run function 2 =)))");
    }
}
