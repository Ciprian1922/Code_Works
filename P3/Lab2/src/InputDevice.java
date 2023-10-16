import java.util.Random;

public class InputDevice {
    public static int[] getNumbers(int N) {
        return new Random().ints(N, 1, 101).toArray();
    }

    public static String getLine() {
        return "The quick brown lazy fox jumps over the lazy dog.";
    }
}
