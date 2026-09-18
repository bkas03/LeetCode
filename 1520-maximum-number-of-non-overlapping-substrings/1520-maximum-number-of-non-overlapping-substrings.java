import java.util.*;

class Solution {
    public List<String> maxNumOfSubstrings(String s) {
        int n = s.length();

        int[] first = new int[26];
        int[] last = new int[26];

        Arrays.fill(first, n);
        Arrays.fill(last, -1);

        // Find the first and last occurrence of every character
        for (int i = 0; i < n; i++) {
            int c = s.charAt(i) - 'a';

            first[c] = Math.min(first[c], i);
            last[c] = i;
        }

        List<int[]> intervals = new ArrayList<>();

        // Construct the smallest valid interval for each character
        for (int c = 0; c < 26; c++) {
            if (last[c] == -1) {
                continue;
            }

            int left = first[c];
            int right = last[c];

            boolean valid = true;

            for (int i = left; i <= right; i++) {
                int current = s.charAt(i) - 'a';

                // This character occurs before the current interval
                if (first[current] < left) {
                    valid = false;
                    break;
                }

                // Expand the interval to include all occurrences
                right = Math.max(right, last[current]);
            }

            if (valid) {
                intervals.add(new int[]{left, right});
            }
        }

        // Sort intervals by their ending position
        intervals.sort((a, b) -> Integer.compare(a[1], b[1]));

        List<String> result = new ArrayList<>();

        int previousEnd = -1;

        for (int[] interval : intervals) {
            int left = interval[0];
            int right = interval[1];

            // Select only non-overlapping intervals
            if (left > previousEnd) {
                result.add(s.substring(left, right + 1));
                previousEnd = right;
            }
        }

        return result;
    }
}