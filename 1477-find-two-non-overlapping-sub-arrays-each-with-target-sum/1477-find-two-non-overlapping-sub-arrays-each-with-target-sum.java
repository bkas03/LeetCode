import java.util.*;

class Solution {
    public int minSumOfLengths(int[] arr, int target) {
        int n = arr.length;

        int[] best = new int[n];
        Arrays.fill(best, Integer.MAX_VALUE);

        int left = 0;
        long sum = 0;

        int answer = Integer.MAX_VALUE;

        for (int right = 0; right < n; right++) {
            sum += arr[right];

            while (sum > target && left <= right) {
                sum -= arr[left];
                left++;
            }

            // Carry forward the best subarray found so far
            if (right > 0) {
                best[right] = best[right - 1];
            }

            // Current window has sum == target
            if (sum == target) {
                int len = right - left + 1;

                // Need a previous subarray that ends before 'left'
                if (left > 0 && best[left - 1] != Integer.MAX_VALUE) {
                    answer = Math.min(answer, len + best[left - 1]);
                }

                best[right] = Math.min(best[right], len);
            }
        }

        return answer == Integer.MAX_VALUE ? -1 : answer;
    }
}