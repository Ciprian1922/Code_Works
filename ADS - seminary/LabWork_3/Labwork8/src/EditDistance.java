import static java.lang.Math.min;

public class EditDistance {
    private static int[][] dp;

    public static void main(String[] args) {
        String str1 = "ARCHERY";
        String str2 = "MARCEL";

        int minEditDistance = calculateEditDistance(str1, str2);
        System.out.println("Minimum Edit Distance: " + minEditDistance);

        String transcript = findTranscript(str1, str2);
        System.out.println("Transcript: " + transcript);
    }

    private static int calculateEditDistance(String str1, String str2) {
        int m = str1.length();
        int n = str2.length();

        dp = new int[m + 1][n + 1];

        for (int i = 0; i <= m; i++) {
            for (int j = 0; j <= n; j++) {
                if (i == 0) {
                    dp[i][j] = j;
                } else if (j == 0) {
                    dp[i][j] = i;
                } else if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = 1 + min(dp[i - 1][j], min(dp[i][j - 1], dp[i - 1][j - 1]));
                }
            }
        }
        return dp[m][n];
    }

    private static String findTranscript(String str1, String str2) {
        StringBuilder transcript = new StringBuilder();

        int i = str1.length();
        int j = str2.length();

        while (i > 0 || j > 0) {
            if (i > 0 && j > 0 && str1.charAt(i - 1) == str2.charAt(j - 1)) {
                transcript.insert(0, str1.charAt(i - 1));
                i--;
                j--;
            } else if (j > 0 && (i == 0 || dp[i][j - 1] + 1 == dp[i][j])) {
                transcript.insert(0, "I");
                j--;
            } else if (i > 0 && (j == 0 || dp[i - 1][j] + 1 == dp[i][j])) {
                transcript.insert(0, "D");
                i--;
            } else {
                transcript.insert(0, "R");
                i--;
                j--;
            }
        }

        return transcript.toString();
    }
}
