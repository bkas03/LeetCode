class Solution {
    public long[] resultArray(int[] nums, int k) {
        long[] ans = new long[k];

        // dp[r] = number of subarrays ending at the previous index
        // whose product % k == r
        long[] dp = new long[k];

        for (int num : nums) {
            int val = num % k;

            // New subarrays ending at the current index
            long[] next = new long[k];

            // Subarray containing only nums[i]
            next[val]++;

            // Extend every subarray ending at the previous index
            for (int r = 0; r < k; r++) {
                if (dp[r] != 0) {
                    int newRemainder = (int) ((long) r * val % k);
                    next[newRemainder] += dp[r];
                }
            }

            // Every subarray ending here contributes to the answer
            for (int r = 0; r < k; r++) {
                ans[r] += next[r];
            }

            dp = next;
        }

        return ans;
    }
}