class Solution {

    public int stoneGameV(int[] stoneValue) {
        int n = stoneValue.length;

        // prefix[i] = sum of stoneValue[0 ... i-1]
        int[] prefix = new int[n + 1];

        for (int i = 0; i < n; i++) {
            prefix[i + 1] = prefix[i] + stoneValue[i];
        }

        // dp[l][r] = maximum score for interval [l, r]
        int[][] dp = new int[n][n];

        // Length of interval
        for (int len = 2; len <= n; len++) {

            for (int l = 0; l + len <= n; l++) {

                int r = l + len - 1;

                // Try every possible split
                for (int k = l; k < r; k++) {

                    int leftSum = prefix[k + 1] - prefix[l];
                    int rightSum = prefix[r + 1] - prefix[k + 1];

                    if (leftSum < rightSum) {

                        // Right side is discarded.
                        // Left side remains.
                        dp[l][r] = Math.max(
                            dp[l][r],
                            leftSum + dp[l][k]
                        );

                    } else if (leftSum > rightSum) {

                        // Left side is discarded.
                        // Right side remains.
                        dp[l][r] = Math.max(
                            dp[l][r],
                            rightSum + dp[k + 1][r]
                        );

                    } else {

                        // Equal sums: Alice chooses either side.
                        dp[l][r] = Math.max(
                            dp[l][r],
                            leftSum + Math.max(
                                dp[l][k],
                                dp[k + 1][r]
                            )
                        );
                    }
                }
            }
        }

        return dp[0][n - 1];
    }
}