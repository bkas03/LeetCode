
class Solution {
    public boolean hasValidPath(char[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        // A valid parentheses string must have even length
        if ((m + n - 1) % 2 == 1) {
            return false;
        }

        java.util.Set<Integer>[] dp = new java.util.HashSet[n];

        for (int j = 0; j < n; j++) {
            dp[j] = new java.util.HashSet<>();
        }

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {

                java.util.Set<Integer> current = new java.util.HashSet<>();

                int value = (grid[i][j] == '(') ? 1 : -1;

                // Starting cell
                if (i == 0 && j == 0) {
                    // Cannot start with ')'
                    if (value == 1) {
                        current.add(1);
                    }
                } else {

                    // From top
                    if (i > 0) {
                        for (int balance : dp[j]) {
                            int newBalance = balance + value;

                            if (newBalance >= 0) {
                                current.add(newBalance);
                            }
                        }
                    }

                    // From left
                    if (j > 0) {
                        for (int balance : dp[j - 1]) {
                            int newBalance = balance + value;

                            if (newBalance >= 0) {
                                current.add(newBalance);
                            }
                        }
                    }
                }

                dp[j] = current;
            }
        }

        // Valid string must finish with balance 0
        return dp[n - 1].contains(0);
    }
}
