public class EditDistance {
    public static void main(String[] args) {
        String str1 = "ARCHERY";
        String str2 = "MARCEL";

        int minEditDistance = findMinEditDistance(str1, str2);
        String transcript = generateTranscript(str1, str2);

        System.out.println("Minimum Edit Distance: " + minEditDistance);
        System.out.println("Transcript: " + transcript);
    }

    private static int findMinEditDistance(String str1, String str2) {
        int minEditDistance = 0;

        for (int i = 0; i < str1.length(); i++) {
            if (i < str2.length() && str1.charAt(i) != str2.charAt(i)) {
                minEditDistance++;
            }
        }

        return minEditDistance;
    }

    private static String generateTranscript(String str1, String str2) {
        StringBuilder transcript = new StringBuilder();

        for (int i = 0; i < str1.length(); i++) {
            if (i < str2.length() && str1.charAt(i) != str2.charAt(i)) {
                transcript.append(str2.charAt(i));
            }
        }

        return transcript.toString();
    }
}
