public class ApproximateMatching {
    public static void main(String[] args) {
        String pattern = "atac";
        String text = "gatataaac";
        int[][] scoringMatrix = {
                {1, -1, -2, -4, 0},
                {0, 0, -3, -2, -1},
                {0, 0, 3, 0, 0},
                {0, 0, 0, 2, -2}
        };

        findBestApproximation(pattern, text, scoringMatrix);
    }

    private static void findBestApproximation(String pattern, String text, int[][] scoringMatrix) {
        int patternLength = pattern.length();
        int textLength = text.length();

        int[][] dp = new int[patternLength + 1][textLength + 1];

        // Initialization
        for (int i = 0; i <= patternLength; i++) {
            dp[i][0] = i * scoringMatrix[i][4]; // Use the last column for gaps
        }
        for (int j = 0; j <= textLength; j++) {
            dp[0][j] = 0;
        }

        // Fill the DP table
        for (int i = 1; i <= patternLength; i++) {
            for (int j = 1; j <= textLength; j++) {
                int match = dp[i - 1][j - 1] + scoringMatrix[i - 1][text.charAt(j - 1) - 'a'];
                int delete = dp[i - 1][j] + scoringMatrix[i][4]; // Use the last column for gaps
                int insert = dp[i][j - 1] + scoringMatrix[4][text.charAt(j - 1) - 'a']; // Use the last row for gaps

                dp[i][j] = Math.max(match, Math.max(delete, insert));
            }
        }

        // Find the position(s) of the best approximation
        int maxScore = Integer.MIN_VALUE;
        for (int j = 1; j <= textLength; j++) {
            if (dp[patternLength][j] > maxScore) {
                maxScore = dp[patternLength][j];
            }
        }

        System.out.println("Best Approximation Position(s):");
        for (int j = 1; j <= textLength; j++) {
            if (dp[patternLength][j] == maxScore) {
                System.out.println("Position: " + j);
            }
        }
    }
}
