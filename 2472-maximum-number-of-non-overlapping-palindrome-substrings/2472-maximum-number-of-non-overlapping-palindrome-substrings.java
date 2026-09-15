
class Solution {
    public int maxPalindromes(String s, int k) {
        int n = s.length();

        // palindrome[l][r] = true if s[l...r] is a palindrome
        boolean[][] palindrome = new boolean[n][n];

        // Build palindrome table
        for (int len = 1; len <= n; len++) {
            for (int l = 0; l + len - 1 < n; l++) {
                int r = l + len - 1;

                if (len == 1) {
                    palindrome[l][r] = true;
                } else if (len == 2) {
                    palindrome[l][r] = (s.charAt(l) == s.charAt(r));
                } else {
                    palindrome[l][r] =
                        (s.charAt(l) == s.charAt(r)) &&
                        palindrome[l + 1][r - 1];
                }
            }
        }

        /*
         * dp[i] = maximum number of valid palindromes
         *         in the first i characters.
         *
         * dp[0] = 0
         */
        int[] dp = new int[n + 1];

        for (int i = 1; i <= n; i++) {
            // Do not use a palindrome ending at i-1.
            dp[i] = dp[i - 1];

            /*
             * Try every palindrome ending at i-1.
             *
             * [l, i-1] must have length >= k.
             */
            for (int l = 0; l <= i - k; l++) {
                if (palindrome[l][i - 1]) {
                    dp[i] = Math.max(dp[i], dp[l] + 1);
                }
            }
        }

        return dp[n];
    }
}
