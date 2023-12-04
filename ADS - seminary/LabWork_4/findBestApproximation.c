#include <stdio.h>
#include <string.h>

#define MAX(a, b) ((a) > (b) ? (a) : (b))

void findBestApproximation(char pattern[], char text[], int scoringMatrix[][5]) {
    int patternLength = strlen(pattern);
    int textLength = strlen(text);

    int dp[patternLength + 1][textLength + 1];

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
            int match = dp[i - 1][j - 1] + scoringMatrix[i - 1][text[j - 1] - 'a'];
            int delete = dp[i - 1][j] + scoringMatrix[i][4]; // Use the last column for gaps
            int insert = dp[i][j - 1] + scoringMatrix[4][text[j - 1] - 'a']; // Use the last row for gaps

            dp[i][j] = MAX(match, MAX(delete, insert));
        }
    }

    // Find the position(s) of the best approximation
    int maxScore = -1;
    for (int j = 1; j <= textLength; j++) {
        if (dp[patternLength][j] > maxScore) {
            maxScore = dp[patternLength][j];
        }
    }

    printf("Best Approximation Position(s):\n");
    for (int j = 1; j <= textLength; j++) {
        if (dp[patternLength][j] == maxScore) {
            printf("Position: %d\n", j);
        }
    }
}

int main() {
    char pattern[] = "atac";
    char text[] = "gatataaac";
    int scoringMatrix[][5] = {
            {1, -1, -2, -4, 0},
            {0, 0, -3, -2, -1},
            {0, 0, 3, 0, 0},
            {0, 0, 0, 2, -2}
    };

    findBestApproximation(pattern, text, scoringMatrix);

    return 0;
}
