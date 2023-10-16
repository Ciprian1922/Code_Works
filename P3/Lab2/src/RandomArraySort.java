import java.util.Arrays;

public class RandomArraySort {
    public static void sortNumbers() {
        int N = 15; // Nr of random numbers

        // Getting random numbers
        int[] randomNumbers = InputDevice.getNumbers(N);

        // Unsorted array
        System.out.println("Unsorted Array:");
        System.out.println(Arrays.toString(randomNumbers));
        System.out.println();

        // Sorting
        Application.sortNumbers(randomNumbers);

        // Print
        System.out.println("Sorted Array:");
        System.out.println(Arrays.toString(randomNumbers));
    }

    public static void wordSizeHistogram() {
        String sentence = InputDevice.getLine();
        int[] histogram = Application.wordSizeHistogram(sentence);

        System.out.println("\nWord Size Histogram:");
        for (int i = 0; i < histogram.length; i++) {
            if (histogram[i] > 0) {
                System.out.println("Words of length " + (i + 1) + ": " + histogram[i]);
            }
        }
    }
}
