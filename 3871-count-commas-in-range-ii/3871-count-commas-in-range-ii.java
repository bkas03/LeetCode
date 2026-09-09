class Solution {
    public long countCommas(long n) {
        long ans = 0;
        long threshold = 1000;

        while (threshold <= n) {
            ans += n - threshold + 1;

            // Prevent overflow
            if (threshold > n / 1000) {
                break;
            }

            threshold *= 1000;
        }

        return ans;
    }
}