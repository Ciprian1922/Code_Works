public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Usage: Main [numbers | string]");
            return;
        }

        for (String arg : args) {
            if (arg.equals("numbers")) {
                RandomArraySort.sortNumbers();
            } else if (arg.equals("string")) {
                RandomArraySort.wordSizeHistogram();
            } else {
                System.out.println("Invalid argument: " + arg);
                System.out.println("Usage: Main [numbers | string]");
            }
        }
    }
}
