public class ApproximateMatching {
    public static void main(String[] args) {
        String pattern = "atac";
        String text = "gatataaac";

        int[][] scores = {
                {1, -1, -2, -4},
                {0, -3, -2, -1},
                {3, 0, 0, 0},
                {2, -2, 0, 0}
        };



        int bestScore = findBestApproximation(pattern, text, scores);
        System.out.println("Best Approximation Score: " + bestScore);
    }

    private static int findBestApproximation(String pattern, String text, int[][] scores) {
        int m = pattern.length();
        int n = text.length();

        int[][] dp = new int[m + 1][n + 1];

        // Initialize the first row
        for (int i = 1; i <= m; i++) {
            dp[i][0] = dp[i - 1][0] + scores[i - 1][3]; // Use the last column
        }

        // Initialize the first column
        for (int j = 1; j <= n; j++) {
            dp[0][j] = dp[0][j - 1] + scores[3][text.charAt(j - 1) - 'a']; // Use the last row
        }

        // Fill in the dynamic programming matrix
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                int match = dp[i - 1][j - 1] + scores[i - 1][text.charAt(j - 1) - 'a'];
                int delete = dp[i][j - 1] + scores[3][text.charAt(j - 1) - 'a']; // Use the last row
                int insert = dp[i - 1][j] + scores[i - 1][3]; // Use the last column

                dp[i][j] = Math.max(match, Math.max(delete, insert));
            }
        }

        return dp[m][n];
    }
}
