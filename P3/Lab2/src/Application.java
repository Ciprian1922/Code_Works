import java.util.Arrays;

public class Application {
    public static void sortNumbers(int[] numbers) {
        Arrays.sort(numbers);
    }

    public static int[] wordSizeHistogram(String sentence) {
        String[] words = sentence.split("\\s+");
        int maxWordLength = 0;

        for (String word : words) {
            maxWordLength = Math.max(maxWordLength, word.length());
        }

        int[] histogram = new int[maxWordLength];

        for (String word : words) {
            int wordLength = word.length();
            if (wordLength > 0) {
                histogram[wordLength - 1]++;
            }
        }

        return histogram;
    }
}
