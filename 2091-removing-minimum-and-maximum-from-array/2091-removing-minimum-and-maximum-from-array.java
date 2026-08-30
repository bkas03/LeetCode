class Solution {
    public int minimumDeletions(int[] nums) {
        int n = nums.length;

        int minIdx = 0;
        int maxIdx = 0;

        // Find indices of minimum and maximum
        for (int i = 1; i < n; i++) {
            if (nums[i] < nums[minIdx]) {
                minIdx = i;
            }

            if (nums[i] > nums[maxIdx]) {
                maxIdx = i;
            }
        }

        int left = Math.min(minIdx, maxIdx);
        int right = Math.max(minIdx, maxIdx);

        // Case 1: both removed from front
        int fromFront = right + 1;

        // Case 2: both removed from back
        int fromBack = n - left;

        // Case 3: one from front, one from back
        int bothSides = (left + 1) + (n - right);

        return Math.min(fromFront, Math.min(fromBack, bothSides));
    }
}