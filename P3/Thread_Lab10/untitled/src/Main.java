import java.util.Random;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        // Example usage
        int[] inputArray = GenerateArray.generateArray(100000000);
        int numThreads = 4;

        // Create an instance of SumThread and invoke its main method
        SumThread.main(inputArray, numThreads);
    }
}