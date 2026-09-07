class Solution {
    public int distinctSubseqII(String s) {
        final long MOD = 1_000_000_007;

        long[] last = new long[26];

        // dp = number of distinct subsequences including ""
        long dp = 1;

        for (char c : s.toCharArray()) {
            int idx = c - 'a';

            long newDp = (2 * dp % MOD - last[idx] + MOD) % MOD;

            // Store the old dp for this character
            last[idx] = dp;

            dp = newDp;
        }

        // Remove the empty subsequence
        return (int) ((dp - 1 + MOD) % MOD);
    }
}