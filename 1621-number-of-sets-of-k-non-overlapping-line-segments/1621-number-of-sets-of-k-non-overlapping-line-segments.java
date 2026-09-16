
class Solution {
    static final long MOD = 1_000_000_007L;

    public int numberOfSets(int n, int k) {
        // dp[j] = number of ways to form j segments
        // while processing the current point.
        long[] dp = new long[k + 1];

        // Initially, there is exactly one way to draw 0 segments.
        dp[0] = 1;

        /*
         * We use:
         *
         * dp[j] after processing i points
         * = dp[j] before + ways to finish a new segment at i.
         *
         * The prefix array keeps track of the sum needed
         * to start a segment at any previous point.
         */
        long[][] f = new long[k + 1][n];

        // 0 segments: one way for every number of processed points.
        for (int i = 0; i < n; i++) {
            f[0][i] = 1;
        }

        for (int j = 1; j <= k; j++) {
            long prefix = 0;

            for (int i = 1; i < n; i++) {
                /*
                 * A segment ending at point i can start at
                 * any point p < i.
                 *
                 * Because segments may share endpoints,
                 * the previous j-1 segments are allowed to
                 * end exactly at p.
                 */
                prefix = (prefix + f[j - 1][i - 1]) % MOD;

                f[j][i] = (f[j][i - 1] + prefix) % MOD;
            }
        }

        return (int) f[k][n - 1];
    }
}
