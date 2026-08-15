class Solution {
    public int longestSubsequence(int[] nums) {
        int xor = 0;
        int n = nums.length;

        for (int x : nums) {
            xor ^= x;
        }

        // Entire array has non-zero XOR
        if (xor != 0) {
            return n;
        }

        // XOR is 0. If there is a non-zero element,
        // remove it and the remaining XOR becomes non-zero.
        for (int x : nums) {
            if (x != 0) {
                return n - 1;
            }
        }

        // All elements are 0
        return 0;
    }
}