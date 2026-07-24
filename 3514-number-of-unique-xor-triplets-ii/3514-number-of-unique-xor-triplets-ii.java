class Solution {
    public int uniqueXorTriplets(int[] nums) {
        final int MAX = 2048;

        boolean[][] dp = new boolean[4][MAX];
        dp[0][0] = true;

        for (int val : nums) {
            boolean[][] next = new boolean[4][MAX];

            // Option: don't use this index
            for (int c = 0; c <= 3; c++) {
                System.arraycopy(dp[c], 0, next[c], 0, MAX);
            }

            for (int cnt = 0; cnt <= 3; cnt++) {
                for (int x = 0; x < MAX; x++) {
                    if (!dp[cnt][x]) continue;

                    // Use this index once
                    if (cnt + 1 <= 3)
                        next[cnt + 1][x ^ val] = true;

                    // Use this index twice
                    if (cnt + 2 <= 3)
                        next[cnt + 2][x] = true;

                    // Use this index three times
                    if (cnt + 3 <= 3)
                        next[cnt + 3][x ^ val] = true;
                }
            }

            dp = next;
        }

        int ans = 0;
        for (boolean b : dp[3]) {
            if (b) ans++;
        }
        return ans;
    }
}