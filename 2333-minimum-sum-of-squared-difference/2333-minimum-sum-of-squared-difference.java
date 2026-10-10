
class Solution {
    public long minSumSquareDiff(int[] nums1, int[] nums2, int k1, int k2) {
        int n = nums1.length;
        long operations = (long) k1 + k2;
        int[] diff = new int[n];

        int maxDiff = 0;
        long sumDiff = 0;

        for (int i = 0; i < n; i++) {
            diff[i] = Math.abs(nums1[i] - nums2[i]);
            maxDiff = Math.max(maxDiff, diff[i]);
            sumDiff += diff[i];
        }

        // If all differences can be reduced to zero
        if (operations >= sumDiff) {
            return 0L;
        }

        // Binary search for the minimum possible maximum difference
        int left = 0, right = maxDiff;

        while (left < right) {
            int mid = left + (right - left) / 2;
            long needed = 0;

            for (int d : diff) {
                if (d > mid) {
                    needed += d - mid;
                }
            }

            if (needed <= operations) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }

        int limit = left;
        long answer = 0;
        long remaining = operations;

        // Reduce every difference greater than limit
        for (int d : diff) {
            if (d > limit) {
                remaining -= d - limit;
                d = limit;
            }
            answer += (long) d * d;
        }

        // Use remaining operations to reduce differences at the limit.
        // Each such operation reduces d^2 by 2*d - 1.
        for (int i = 0; i < n && remaining > 0; i++) {
            int d = Math.abs(nums1[i] - nums2[i]);

            if (d >= limit && d > 0) {
                answer -= 2L * limit - 1;
                remaining--;
            }
        }

        return answer;
    }
}
